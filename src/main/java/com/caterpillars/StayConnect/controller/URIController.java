package com.caterpillars.StayConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.caterpillars.StayConnect.model.repository.AccommodationRepository;

@Controller
public class URIController {

  private final AccommodationRepository accommodationRepository;

  public URIController(AccommodationRepository accommodationRepository) {
    this.accommodationRepository = accommodationRepository;
  }

  @GetMapping("/")
  public String index(Model model) {

    return "pages/index";
  }

}