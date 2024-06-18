package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private PortOnePaymentService portOnePaymentService;

    @Autowired
    private ReservationRepository reservationRepository;

    // getPaymentDetails 메소드 호출
    public Reservation createReservation(String imp_uid, User user, RoomInfo roomInfo, LocalDateTime checkIn, LocalDateTime checkOut) {
        PaymentDto paymentDto = portOnePaymentService.getPaymentDetails(imp_uid);

        Reservation reservation = Reservation.builder()
                .user(user)
                .roomInfo(roomInfo)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .reservationAt(LocalDateTime.now())
                .price(paymentDto.getPaid_amount())
                .reservationType(paymentDto.getPay_method())
                .build();

        return reservationRepository.save(reservation);
    }
}
