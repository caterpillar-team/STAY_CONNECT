package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class URIController {

  @GetMapping("/")
  public String index(Model model) {

    return "pages/index";
  }

}
