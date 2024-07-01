package com.caterpillars.StayConnect.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;

@Service
public class ReservationService {

        @Autowired
        private PortOnePaymentService portOnePaymentService;

        @Autowired
        private ReservationRepository reservationRepository;

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
        public Reservation createReservation(String imp_uid, User user, RoomInfo roomInfo, LocalDateTime checkIn,
                        LocalDateTime checkOut, PaymentDto paymentDto) {
                // PaymentDto paymentDto = portOnePaymentService.getPaymentDetails(imp_uid);

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
}