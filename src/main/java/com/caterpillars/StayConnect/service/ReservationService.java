package com.caterpillars.StayConnect.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.dto.ReservationDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private PortOneTokenResponse portOneTokenResponse;

    @Value("${PORTONE_API_KEY}")
    private String apiKey;

    @Value("${PORTONE_SECRET_KEY}")
    private String apiSecret;

    @Transactional(readOnly = true)
    public List<Object[]> getMonthlyReservationCount() {
        return reservationRepository.findMonthlyReservationCount();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getReservationsByRegion() {
        List<Reservation> reservations = reservationRepository.findAll();

        Map<String, Long> reservationCountByRegion = reservations.stream()
                .collect(Collectors.groupingBy(reservation -> {
                    String address = reservation.getRoomInfo().getAccommodation().getAddress();
                    String[] addressParts = address.split(" ");
                    return addressParts[0] + " " + addressParts[1];
                }, Collectors.counting()));

        return reservationCountByRegion.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("region", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Reservation createReservation(String imp_uid, User user, RoomInfo roomInfo, LocalDateTime checkIn,
            LocalDateTime checkOut, PaymentDto paymentDto) {

        Reservation reservation = Reservation.builder()
                .imp_uid(imp_uid)
                .user(user)
                .roomInfo(roomInfo)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .reservationAt(LocalDateTime.now())
                .price(paymentDto.getPaid_amount())
                .reservationType(paymentDto.getMerchant_uid())
                .merchant_uid(paymentDto.getMerchant_uid())
                .pay_method(paymentDto.getPay_method())
                .build();

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(long id) {
        reservationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ReservationDto getReservationDetails(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("예약을 조회할 수 없습니다."));

        User user = reservation.getUser();
        RoomInfo roomInfo = reservation.getRoomInfo();
        Accommodation accommodation = roomInfo.getAccommodation();

        return ReservationDto.builder()
                .id(reservation.getId())
                .realName(user.getRealName())
                .roomType(roomInfo.getRoomType())
                .bedType(roomInfo.getBedType())
                .accommodationName(accommodation.getName())
                .price(reservation.getPrice())
                .payMethod(reservation.getPay_method())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReservationDto convertToDto(Reservation reservation) {
        User user = reservation.getUser();
        RoomInfo roomInfo = reservation.getRoomInfo();
        Accommodation accommodation = roomInfo.getAccommodation();

        return ReservationDto.builder()
                .id(reservation.getId())
                .realName(user.getRealName())
                .roomType(roomInfo.getRoomType())
                .bedType(roomInfo.getBedType())
                .accommodationName(accommodation.getName())
                .price(reservation.getPrice())
                .payMethod(reservation.getPay_method())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .build();
    }

    @Transactional
    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public @ResponseBody void getToken() {

        String url = "https://api.iamport.kr/users/getToken";

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", apiKey);
        params.add("imp_secret", apiSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response = rt.exchange(url, HttpMethod.POST, entity,
                PortOneTokenResponse.class);

        this.portOneTokenResponse = response.getBody();

    }

    @Transactional
    public Map<String, Object> cancelReservation(Long reservationId) {
        System.out.println("received request : delete");

        Map<String, Object> response = new HashMap<>();

        try {
            deleteReservationById(reservationId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "예약 삭제 중 오류 발생: " + e.getMessage());
        }
        return response;
    }

    @Transactional
    public boolean cancel_reservation(Long reservationId) {

        getToken();
        Optional<Reservation> reservation_optional = reservationRepository.findById(reservationId);
        if (reservation_optional.isEmpty())
            return false;

        Reservation reservation = reservation_optional.get();
        String imp_uid = reservation.getImp_uid();
        String merchant_uid = reservation.getMerchant_uid();

        String url = "https://api.iamport.kr/payments/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + portOneTokenResponse.getResponse().getAccess_token());
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_uid", imp_uid);
        params.add("merchant_uid", merchant_uid);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        System.out.println(resp);
        System.out.println(resp.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(resp.getBody());
            JsonNode cancelHistoryNode = rootNode.path("response").path("cancel_history");
            if (cancelHistoryNode.isArray() && cancelHistoryNode.size() > 0) {
                JsonNode firstCancelHistory = cancelHistoryNode.get(0);
                String cancellationId = firstCancelHistory.path("cancellation_id").asText();
                if (!cancellationId.isEmpty()) {
                    cancelReservation(reservationId);
                } else {
                    System.out.println("error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Transactional(readOnly = true)
    public int getTotalReservations() {

        return reservationRepository.countTotalReservations();
    }

    @Transactional(readOnly = true)
    public int getTotalSales() {
        return reservationRepository.totalSales();
    }

    // ---------------------------
    // AccessToken 발급 Class
    // ---------------------------
    @Data
    private static class TokenResponse {
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse {
        public int code;
        public Object message;
        public TokenResponse response;
    }

}
