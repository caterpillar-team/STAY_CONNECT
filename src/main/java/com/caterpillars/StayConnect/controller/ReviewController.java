package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.service.ReviewService;
import com.caterpillars.StayConnect.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/accom/detail/{id}")
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 삭제
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId, RedirectAttributes redirectAttributes) {
        log.info("POST /accom/detail/{id}/delete id " + reviewId);
        ReviewDto dto = reviewService.getReviewDto(reviewId);

        try {
            // 서비스 실행
            boolean isDelete = reviewService.deleteReview(reviewId);

            if (isDelete) {
                log.info("리뷰가 성공적으로 삭제되었습니다.");
                // 삭제된 리뷰의 ID를 사용하여 다른 작업을 수행
                log.info("accId : " + dto.getAccId());
                return "redirect:/accom/detail/" + dto.getAccId();
            } else {
                // 리뷰 삭제에 실패한 경우 처리
                log.error("리뷰 삭제에 실패했습니다.");
                return "리뷰 삭제에 실패";
            }
        } catch (Exception e) {
            // 리뷰를 찾을 수 없는 경우
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰를 찾을 수 없습니다.");
            return "redirect:/error";
        }
    }

    // 리뷰 수정 POST
    @PostMapping("/update/{reviewId}")
    public String postUpdateReview(@PathVariable("reviewId") long reviewId, @Valid @ModelAttribute("reviewDto") ReviewDto dto, BindingResult bindingResult, Model model)
            throws IOException {
        log.info("POST /accom/detail/"+dto.getAccId()+"/update/"+dto.getReviewId()+" dto " + dto);

        if (bindingResult.hasErrors()) {
            return "redirect:/accom/detail/"+dto.getAccId();
        }

        // 서비스 실행
        boolean isUpdate = reviewService.updateReview(dto);

        if (isUpdate) {
            return "redirect:/accom/detail/" + dto.getAccId();
        }
        return "error/default";
    }

}