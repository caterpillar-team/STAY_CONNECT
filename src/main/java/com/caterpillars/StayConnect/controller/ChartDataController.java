package com.caterpillars.StayConnect.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caterpillars.StayConnect.service.ChartDataService;
import com.caterpillars.StayConnect.service.ReservationService;

@RestController
@RequestMapping("/api")
public class ChartDataController {

  @Autowired
  private ReservationService reservationService;
  @Autowired
  private ChartDataService chartDataService;

  @GetMapping("/reservationStats")
  public List<Object[]> getMonthlyReservationStats() {
    return reservationService.getMonthlyReservationCount();
  }

  @GetMapping("/ageStats")
  public List<Map<String, Object>> getAgeGroupCounts() {
    return chartDataService.getAgeGroupCounts();
  }

  @GetMapping("/reservationsByRegion")
  public List<Map<String, Object>> getReservationsByRegion() {
    return reservationService.getReservationsByRegion();
  }
}
