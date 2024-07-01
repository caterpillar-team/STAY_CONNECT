package com.caterpillars.StayConnect.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT YEAR(r.checkIn) as year, MONTH(r.checkIn) as month, COUNT(r) as count " +
            "FROM Reservation r " +
            "GROUP BY YEAR(r.checkIn), MONTH(r.checkIn) " +
            "ORDER BY year, month")
    List<Object[]> findMonthlyReservationCount();

}
