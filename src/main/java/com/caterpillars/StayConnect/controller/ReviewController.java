package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
<<<<<<< Updated upstream
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/accommodation/detail")
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @PostMapping("/{accId}/addReview")
    public String postAddReview(@PathVariable("accId") Long accId, @Valid @ModelAttribute("reviewDto") ReviewDto dto,
                                BindingResult bindingResult, Model model) {
        log.info("POST /accommodation/detail/" + accId + "/addReview " + dto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값에 오류가 있습니다. 다시 시도해주세요.");
            return null;
        }

        RoomInfo roomInfo = reviewService.findRoomInfoById(dto.getRoomInfoId());
        log.info("findById result: " + roomInfo);

        if (roomInfo == null) {
            model.addAttribute("errorMessage", "해당 숙소 정보를 찾을 수 없습니다.");
            return null;
        }

        boolean isAdd = reviewService.addReview(dto, roomInfo);

        if (isAdd) {
            log.info("isAdd accID : " + accId);
            return "redirect:/accommodation/detail/" + accId;
        }

        model.addAttribute("errorMessage", "리뷰를 추가하는 도중 오류가 발생했습니다. 다시 시도해주세요.");
        return null;
    }

    // 리뷰 삭제
    @DeleteMapping("/{accId}/delete/{reviewId}")
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("reviewId") Long reviewId, RedirectAttributes redirectAttributes) {
        log.info("DELETE /accommodation/detail/{id}/delete id " + reviewId);
        Map<String, Object> response = new HashMap<>();

        try {
            ReviewDto dto = reviewService.getReviewDto(reviewId);
            if (dto == null) {
                response.put("message", "리뷰를 찾을 수 없습니다.");
            }

            // 서비스 실행
            boolean isDelete = reviewService.deleteReview(reviewId);

            if (isDelete) {
                response.put("message", "리뷰가 성공적으로 삭제되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 리뷰 삭제에 실패한 경우 처리
                response.put("message", "리뷰 삭제에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            // 리뷰를 찾을 수 없는 경우
            log.error("리뷰 삭제 중 오류가 발생했습니다.", e);
            response.put("message", "리뷰 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/update/review")
    public @ResponseBody ResponseEntity<Map<String, Object>> updateReview(@ModelAttribute ReviewDto reviewDto) {
        log.info("POST /user/accom/detail/update/review : " + reviewDto);
        Map<String, Object> result = reviewService.updateReview(reviewDto);

        if (result.get("message") == null)
            result.put("message", "리뷰 업데이트 성공!");


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
=======
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
>>>>>>> Stashed changes
