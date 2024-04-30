package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class URIController {

  @GetMapping("/signup")
  public String showSignUpForm() {
    return "auth/signup";
  }

  @GetMapping("/signin")
  public String signin() {
    return "auth/signin";
  }

  @GetMapping("/mypage")
  public String myPage() {
    return "auth/mypage";
  }

  @GetMapping("/admin/user/list")
  public String getUserList() {
    return "admin_user";
  }

  @GetMapping("/test")
  public String test() {
    return "test/test";
  }

}
