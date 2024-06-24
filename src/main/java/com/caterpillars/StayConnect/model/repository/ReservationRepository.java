package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT YEAR(r.checkIn) as year, MONTH(r.checkIn) as month, COUNT(r) as count " +
            "FROM Reservation r " +
            "GROUP BY YEAR(r.checkIn), MONTH(r.checkIn) " +
            "ORDER BY year, month")
    List<Object[]> findMonthlyReservationCount();

}
