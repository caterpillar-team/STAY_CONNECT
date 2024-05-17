package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/mypage")
  public String getMyPage() {
    return "pages/user/myPage";
  }
}
