package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            return "redirect:/error";
        }

        RoomInfo roomInfo = reviewService.findRoomInfoById(dto.getRoomInfoId());
        log.info("findById result: " + roomInfo);

        if (roomInfo == null) {
            model.addAttribute("errorMessage", "해당 숙소 정보를 찾을 수 없습니다.");
            return "redirect:/error";
        }

        boolean isAdd = reviewService.addReview(dto, roomInfo);

        if (isAdd) {
            log.info("isAdd accID : " + accId);
            return "redirect:/accommodation/detail/" + accId; // 리다이렉트 처리 부분
        }

        model.addAttribute("errorMessage", "리뷰를 추가하는 도중 오류가 발생했습니다. 다시 시도해주세요.");
        return "redirect:/error";
    }

    // 리뷰 삭제
    @PostMapping("/{accId}/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId, RedirectAttributes redirectAttributes) {
        log.info("POST /accommodation/detail/{id}/delete id " + reviewId);
        ReviewDto dto = reviewService.getReviewDto(reviewId);

        try {
            // 서비스 실행
            boolean isDelete = reviewService.deleteReview(reviewId);

            if (isDelete) {
                log.info("리뷰가 성공적으로 삭제되었습니다.");
                // 삭제된 리뷰의 ID를 사용하여 다른 작업을 수행
                log.info("accId : " + dto.getAccId());
                return "redirect:/accommodation/detail/" + dto.getAccId();
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

    @PostMapping("/update/review")
    public String updateReview(@ModelAttribute("reviewDto") ReviewDto reviewDto, RedirectAttributes rttr) {
        log.info("POST /user/accom/detail/update/review : " + reviewDto);
        reviewService.updateReview(reviewDto);
        rttr.addFlashAttribute("message", "리뷰를 수정하였습니다");
        return "redirect:/accommodation/detail/" + reviewDto.getAccId(); // 수정 후 숙소 상세 페이지로 리다이렉트
    }

}