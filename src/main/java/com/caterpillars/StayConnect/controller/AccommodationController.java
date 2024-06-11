package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accom")
public class AccommodationController {

@Autowired
private RoomInfoRepository roomInfoRepository;
@Autowired
private ReviewRepository reviewRepository;
@Autowired
private ReviewService reviewService;

    @GetMapping("/detail/{id}")
    public String accom_detail(@PathVariable long id, Model model) {
        // 파라미터
        Optional<RoomInfo> result = roomInfoRepository.findById(id);
        // 유효성, 서비스
        if(result.isPresent()) {
            RoomInfo roomInfo = result.get();
            if (roomInfo.getAccommodation() != null) {
                List<Review> reviews = reviewRepository.findByRoomInfo(roomInfo);
                double averageRating = reviewService.calculateAverageRating(reviews);
                List<RoomInfo> accoms = roomInfoRepository.findByAccommodation(roomInfo.getAccommodation());
                model.addAttribute("accom", roomInfo);
                model.addAttribute("accoms", accoms);
                model.addAttribute("reviews", reviews);
                model.addAttribute("averageRating", averageRating);

                // SecurityContextHolder를 사용하여 Authentication 객체 가져오기
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currentUser = (authentication != null) ? authentication.getName() : null;
                model.addAttribute("currentUser", currentUser);

                System.out.println("Number of reviews found: " + reviews.size());
                return "pages/accommodation/accom_detail";
            } else {
                // Accommodation 객체가 null인 경우 처리
                return "redirect:/error";
            }
        } else {
            return "redirect:/";
        }
    }



}
