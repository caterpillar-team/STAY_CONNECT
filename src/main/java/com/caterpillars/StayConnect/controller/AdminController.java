package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("/reviewList")
  public String getAdminReviewList() {
    return "pages/admin/reviewList";
  }

  @GetMapping("/userList")
  public String getAdminUserList() {
    return "pages/admin/userList";
  }
}
