package com.caterpillars.StayConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    
    
    public List<Object[]> getMonthlyReservationCount() {
        return reservationRepository.findMonthlyReservationCount();
    }

   
}
