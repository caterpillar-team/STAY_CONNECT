package com.caterpillars.StayConnect.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ChartDataService;
import com.caterpillars.StayConnect.service.InquiryService;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.ReviewService;
import com.caterpillars.StayConnect.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ChartDataService chartDataService;

  @Autowired
  private InquiryService inquiryService;

  @Autowired
  private ObjectMapper objectMapper;


  private void addCommonAttributes(Model model) {
    model.addAttribute("lastMessages", inquiryService.getLastMessages());
}



  @GetMapping("/dashBoard")
  public String getCharts(Model model) throws JsonProcessingException {
    List<Object[]> reservationStats = reservationService.getMonthlyReservationCount();
    List<Map<String, Object>> ageStats = chartDataService.getAgeGroupCounts();
    List<Map<String, Object>> regionStats = reservationService.getReservationsByRegion();
    


    String reservationStatsJson = objectMapper.writeValueAsString(reservationStats);

    model.addAttribute("reservationStats", reservationStatsJson);
    model.addAttribute("ageStats", ageStats);
    model.addAttribute("reservationsByRegion", regionStats);

    addCommonAttributes(model);


    return "pages/admin/dashBoard";
  }

  @GetMapping("/userList")
  public String getAdminUserList(@RequestParam(value = "username", required = false) String realName,Model model) {
    List<User> users;
    if (realName !=null && !realName.isEmpty()){
      users = userService.findByRealName(realName);
    }else{
      users = userService.getAllUsers();
    }

    model.addAttribute("userList", users);
    // model.addAttribute("userList", userService.getAllUsers());
    model.addAttribute("lastMessages", inquiryService.getLastMessages());
    addCommonAttributes(model);

    return "pages/admin/userList";

  }

  @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/userList";
    }

  @GetMapping("/reviewList")
  public String getAdminReviewList(Model model) throws JsonProcessingException {

    model.addAttribute("reviewList", reviewService.getAllReviews());
    model.addAttribute("lastMessages", inquiryService.getLastMessages());
    addCommonAttributes(model);

    return "pages/admin/reviewList";
  }

  // @GetMapping("/dashBoard")
  // public String getAdminHome() {
  //     return "pages/admin/dashBoard";
  // }
  
  
}
