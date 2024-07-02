package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.service.AccommodationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SearchController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchAccommodations(
            @RequestParam(name = "searchText", required = false) String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 8);
        Page<AccommodationDto> accommodationPage = accommodationService.search(searchText, pageable);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 7, 1);
        int endPage = Math.min(nowPage + 5, accommodationPage.getTotalPages());

        Map<String, Object> response = new HashMap<>();
        response.put("accommodations", accommodationPage.getContent());
        response.put("nowPage", nowPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);
        response.put("totalPages", accommodationPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}