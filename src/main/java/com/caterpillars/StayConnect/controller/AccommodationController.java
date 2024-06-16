package com.caterpillars.StayConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/accom")
public class AccommodationController {

    @Autowired
    private RoomInfoRepository roomInfoRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/detail/{accId}")
    public String accom_detail(@PathVariable("accId") long accId, Model model) {
        log.info("/detail/" + accId + " accId 실행");
        // accommodationId로 RoomInfo 리스트를 조회
        List<RoomInfo> result = roomInfoRepository.findByAccommodationId(accId);
        log.info("DTO: " + result);

        // 조회 결과가 비어있지 않은지 확인
        if (!result.isEmpty()) {
            RoomInfo roomInfo = result.get(0); // 첫 번째 RoomInfo 객체를 가져옴
            log.info("roomInfo ID: " + roomInfo.getId());

            // 해당 객실에 속하는 숙소 정보 조회
            Accommodation accommodation = roomInfo.getAccommodation();
            log.info("accommodation ID: " + accommodation.getId());

            if (roomInfo.getAccommodation() != null) {
                // 해당 객실에 대한 리뷰 정보 조회
                List<Review> reviews = reviewRepository.findByRoomInfo(roomInfo);
                // 평균 평점 계산
                double averageRating = reviewService.calculateAverageRating(reviews);
                // 해당 숙소에 속하는 모든 객실 정보 조회
                List<RoomInfo> accoms = roomInfoRepository.findByAccommodation(accommodation);

                // 모델에 필요한 정보 추가
                model.addAttribute("accom", roomInfo);
                model.addAttribute("reviews", reviews);
                model.addAttribute("averageRating", averageRating);

                // 현재 인증된 사용자 정보
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currentUser = (authentication != null) ? authentication.getName() : null;
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("reviewDto", new ReviewDto());

                System.out.println("Number of reviews found: " + reviews.size());
                return "pages/accommodation/detail";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }
}