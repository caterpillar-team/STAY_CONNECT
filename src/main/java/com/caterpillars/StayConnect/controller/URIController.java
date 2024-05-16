package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class URIController {

  @GetMapping("/")
  public String index() {
    return "pages/index";
  }

  @GetMapping("/signin")
  public String getSignIn() {
    return "pages/auth/signIn";
  }

  @GetMapping("/mypage")
  public String getMyPage() {
    return "pages/user/myPage";
  }

  @GetMapping("/admin/reviewList")
  public String getAdminReviewList() {
    return "pages/admin/reviewList";
  }

  @GetMapping("/admin/userList")
  public String getAdminUserList() {
    return "pages/admin/userList";
  }
}