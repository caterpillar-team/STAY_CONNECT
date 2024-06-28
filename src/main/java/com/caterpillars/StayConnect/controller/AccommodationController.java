package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.AccommodationService;
import com.caterpillars.StayConnect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/accommodation")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private UserService userService;

    @GetMapping("/detail/{accId}")
    public String accom_detail(@PathVariable("accId") Long accId, Model model,
                               @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("/detail/" + accId + " accId 실행");

        AccommodationDto accommodationDto = accommodationService.getAccommodationDto(accId, pageable);

        if (accommodationDto != null) {
            // 현재 사용자 정보
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "";
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("reviewDto", new ReviewDto());

            List<Review> allReviews = accommodationService.findAllReviews(accId);
            model.addAttribute("allReviews", allReviews);

            model.addAttribute("accommodationDto", accommodationDto);
            model.addAttribute("totalReviews", accommodationDto.getTotalReviews());
            model.addAttribute("nowPage", accommodationDto.getNowPage());
            model.addAttribute("startPage", accommodationDto.getStartPage());
            model.addAttribute("endPage", accommodationDto.getEndPage());

            if (authentication != null && authentication.isAuthenticated()) {
                Optional<User> userOptional = userService.findByUsername(authentication.getName());
                log.info("userOptional" + userOptional);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    System.out.println(user.getPhoneNumber());

                    model.addAttribute("phoneNumber", user.getPhoneNumber());
                    model.addAttribute("userId", user.getId());
                    model.addAttribute("roomInfos", accommodationDto.getRoomInfos());

                } else {
                    log.warn("User not found for username: " + authentication.getName());
                }
            }

            return "pages/accommodation/detail";
        } else {
            return null;
        }
    }
}