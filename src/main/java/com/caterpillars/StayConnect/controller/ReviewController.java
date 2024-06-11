package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.service.ReviewService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private UserService userService;


    @PostMapping("/addReview")
    public String addReview(@RequestParam("rate") int rate,
                            @RequestParam("contents") String contents,
                            @RequestParam("roomInfoId") Long roomInfoId) {
        // 현재 사용자 정보를 가져오는 로직 필요
        User currentUser = getCurrentUser();

        RoomInfo roomInfo = roomInfoService.findById(roomInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room info ID: " + roomInfoId));

//        Review review = new Review();
//        review.setRate(rate);
//        review.setContents(contents);
//        review.setRoomInfo(roomInfo);
//        review.setUser(currentUser);
//        review.setCreatedAt(LocalDateTime.now());

        Review review = Review.builder()
                .rate(rate)
                .contents(contents)
                .roomInfo(roomInfo)
                .user(currentUser)
                .createdAt(LocalDateTime.now())
                .build();

        reviewService.saveReview(review);

        return "redirect:/accom/detail/{id}";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findByUsername(userDetails.getUsername());
        }
        return null;
    }
}
