package com.caterpillars.StayConnect.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caterpillars.StayConnect.service.AdminService;
import com.caterpillars.StayConnect.service.AuthService;
import com.caterpillars.StayConnect.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/admin")
public class AdminController {

  
  @Autowired
  private AuthService authService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private AdminService adminService;

  @Autowired
    private ObjectMapper objectMapper;

  
  


  
  public AdminController(AuthService authService){
    this.authService=authService;
  }

  @GetMapping("/home")
  public String getCharts(Model model) throws JsonProcessingException {
    List<Object[]> reservationStats = reservationService.getMonthlyReservationCount();
    List<Object[]> userStats = adminService.getAllUsersByAge();
    
    String reservationStatsJson = objectMapper.writeValueAsString(reservationStats);
    String userStatsJson = objectMapper.writeValueAsString(userStats);

        model.addAttribute("reservationStats", reservationStatsJson);
        model.addAttribute("userStats", userStatsJson);
      
      return "pages/admin/adminHome";
  }
  


  @GetMapping("/userList")
  public String getAdminUserList(Model model) {
    
    model.addAttribute("userList",adminService.getAllUsers());
    
    
    return "pages/admin/userList";
    
  }


  @GetMapping("/reviewList")
  public String getAdminReviewList(Model model) throws JsonProcessingException {

    model.addAttribute("reviewList", adminService.getAllReviews());
    
    return "pages/admin/reviewList";
  }


  
}
