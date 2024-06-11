package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PortOnePaymentService portOnePaymentService;

    public Reservation savePayment(String paymentId, Reservation reservation) {
        // 결제금액 가져오기
        int amount = portOnePaymentService.getPaymentAmount(paymentId);

        reservation.setPrice(amount);

        return reservationRepository.save(reservation);
    }


}
