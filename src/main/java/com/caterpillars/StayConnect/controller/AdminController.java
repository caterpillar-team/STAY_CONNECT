package com.caterpillars.StayConnect.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caterpillars.StayConnect.service.ChartDataService;
import com.caterpillars.StayConnect.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin")
public class AdminController {

  // @Autowired
  // private AuthService authService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ChartDataService adminService;

  @Autowired
  private ObjectMapper objectMapper;

  @GetMapping("/home")
  public String getCharts(Model model) throws JsonProcessingException {
    List<Object[]> reservationStats = reservationService.getMonthlyReservationCount();
    List<Map<String, Object>> ageStats = adminService.getAgeGroupCounts();
    List<Map<String, Object>> regionStats = reservationService.getReservationsByRegion();

    String reservationStatsJson = objectMapper.writeValueAsString(reservationStats);

    model.addAttribute("reservationStats", reservationStatsJson);
    model.addAttribute("ageStats", ageStats);
    model.addAttribute("reservationsByRegion", regionStats);

    return "pages/admin/home";
  }

  @GetMapping("/userList")
  public String getAdminUserList(Model model) {

    model.addAttribute("userList", adminService.getAllUsers());

    return "pages/admin/userList";

  }

  @GetMapping("/reviewList")
  public String getAdminReviewList(Model model) throws JsonProcessingException {

    model.addAttribute("reviewList", adminService.getAllReviews());

    return "pages/admin/reviewList";
  }

}
