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
                .map(entry -> { Map<String, Object> map = new HashMap<>();
                map.put("region", entry.getKey());
                map.put("count",entry.getValue());
                return map;
                })
                .collect(Collectors.toList());
        }
   
}
