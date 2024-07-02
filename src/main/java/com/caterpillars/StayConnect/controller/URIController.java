package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.service.AccommodationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class URIController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping("/")
    public String index(@RequestParam(name = "searchText", required = false) String searchText,
                        @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        log.info("/ 실행");

        Pageable pageable = PageRequest.of(page, 8);
        Page<AccommodationDto> accommodationPage = accommodationService.search(searchText, pageable);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 7, 1);
        int endPage = Math.min(nowPage + 5, accommodationPage.getTotalPages());

        model.addAttribute("accommodations", accommodationPage.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchText", searchText); // 검색어를 모델에 추가

        return "pages/index";
    }

}