package com.caterpillars.StayConnect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.service.AccommodationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class URIController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 8);

        // 숙소 목록 조회 및 DTO 변환
        Page<Accommodation> accommodationPage = accommodationService.findAllAccommodations(pageable);
        List<AccommodationDto> accommodationDtos = accommodationService.getAccommodationDtos(accommodationPage);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 7, 1);
        int endPage = Math.min(nowPage + 5, accommodationPage.getTotalPages());

        model.addAttribute("accommodations", accommodationDtos);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "pages/index";
    }

    @GetMapping("/accommodation")
    public ResponseEntity<Map<String, Object>> getAccommodations(
            @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 8);

        // 숙소 목록 조회 및 DTO 변환
        Page<Accommodation> accommodationPage = accommodationService.findAllAccommodations(pageable);
        List<AccommodationDto> accommodationDtos = accommodationService.getAccommodationDtos(accommodationPage);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 7, 1);
        int endPage = Math.min(nowPage + 5, accommodationPage.getTotalPages());

        Map<String, Object> response = new HashMap<>();
        response.put("accommodations", accommodationDtos);
        response.put("nowPage", nowPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);
        response.put("totalPages", accommodationPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}