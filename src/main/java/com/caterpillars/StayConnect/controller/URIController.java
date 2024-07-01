package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.service.AccommodationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class URIController {

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private RoomInfoService roomInfoService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        log.info("/ 실행");

        Pageable pageable = PageRequest.of(page, 8);

        // 숙소 목록 조회
        Page<Accommodation> accommodationPage = accommodationService.findAllAccommodations(pageable);
        List<Accommodation> accommodations = accommodationPage.getContent();
        List<AccommodationDto> accommodationDtos = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            List<RoomInfo> roomInfos = roomInfoService.findByAccommodationId(accommodation.getId());
            int minPrice = accommodationService.findMinPrice(roomInfos);

            AccommodationDto dto = accommodationService.convertToDto(accommodation, minPrice);
            accommodationDtos.add(dto);
        }

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 7, 1);
        int endPage = Math.min(nowPage + 5, accommodationPage.getTotalPages());

        model.addAttribute("accommodations", accommodationDtos);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "pages/index";
    }

}