package com.caterpillars.StayConnect.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.service.AccommodationService;
import com.caterpillars.StayConnect.service.RoomInfoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class URIController {

  @Autowired
  private AccommodationService accommodationService;

  @Autowired
  private RoomInfoService roomInfoService;

  @GetMapping("/")
  public String index(Model model) {
    log.info("/ 실행");
    // 숙소 목록 조회
    List<Accommodation> accommodations = accommodationService.findAllAccommodations();
    List<AccommodationDto> accommodationDtos = new ArrayList<>();

    for (Accommodation accommodation : accommodations) {
      List<RoomInfo> roomInfos = roomInfoService.findByAccommodationId(accommodation.getId());
      int minPrice = accommodationService.findMinPrice(roomInfos);

      AccommodationDto dto = accommodationService.convertToDto(accommodation, minPrice);
      accommodationDtos.add(dto);
    }

    model.addAttribute("accommodations", accommodationDtos);

    return "pages/index";
  }

}