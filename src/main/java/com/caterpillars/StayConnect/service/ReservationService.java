package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.dto.ReservationDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private PortOnePaymentService portOnePaymentService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Object[]> getMonthlyReservationCount() {
        return reservationRepository.findMonthlyReservationCount();
    }

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

    // getPaymentDetails 메소드 호출
    public Reservation createReservation(String imp_uid, User user, RoomInfo roomInfo, LocalDateTime checkIn, LocalDateTime checkOut, PaymentDto paymentDto) {
//        PaymentDto paymentDto = portOnePaymentService.getPaymentDetails(imp_uid);

        Reservation reservation = Reservation.builder()
                .imp_uid(imp_uid)
                .user(user)
                .roomInfo(roomInfo)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .reservationAt(LocalDateTime.now())
                .price(paymentDto.getPaid_amount())
                .reservationType(paymentDto.getMerchant_uid())
                .pay_method(paymentDto.getPay_method())
                .build();

        return reservationRepository.save(reservation);
    }

    public void cancelReservation(long id) {
        reservationRepository.deleteById(id);
    }

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

    public List<ReservationDto> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream() // stream : 컬렉션 데이터를 처리할 때 사용할 수 있는 유연한 API를 제공
                .map(this::convertToDto)    // converToDto(Reservation 객체를 ReservationDto 객체로 변환) 메서드를 사용하여 Dto객체로 변환.
                .collect(Collectors.toList());  // 변환된 ReservationDto 객체들을 리스트로 수집하여 반환
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
        System.out.println("(ReservationService) Deleting reservation with ID: " + reservationId); // 디버깅 로그 추가
        reservationRepository.deleteById(reservationId);
    }


}
