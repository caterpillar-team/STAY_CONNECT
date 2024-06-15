package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/accom/detail")
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/{accId}/addReview")
    public String postAddReview(@PathVariable("accId") Long accId, @Valid @ModelAttribute("reviewDto") ReviewDto dto, BindingResult bindingResult, Model model) {
        log.info("POST /accom/detail/" + accId + "/addReview " + dto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값에 오류가 있습니다. 다시 시도해주세요.");
            return "redirect:/error";
        }

        Optional<RoomInfo> roomInfoOptional = roomInfoRepository.findById(dto.getRoomInfoId());
        log.info("findById result: " + roomInfoOptional);

        if (roomInfoOptional.isEmpty()) {
            model.addAttribute("errorMessage", "해당 숙소 정보를 찾을 수 없습니다.");
            return "redirect:/error";
        }

        RoomInfo roomInfo = roomInfoOptional.get();
        Long accommodationId = roomInfo.getAccommodation() != null ? roomInfo.getAccommodation().getId() : null;
        log.info("RoomInfo ID: " + roomInfo.getId());
        log.info("Accommodation ID from RoomInfo: " + accommodationId);

        if (accommodationId == null) {
            model.addAttribute("errorMessage", "숙소 정보가 유효하지 않습니다.");
            return "redirect:/error";
        }

        dto.setAccId(accommodationId);
        log.info("setAccId : " + accommodationId);
        boolean isAdd = reviewService.addReview(dto, roomInfo);

        if (isAdd) {
            log.info("isAdd accID : " + dto.getAccId());
            return "redirect:/accom/detail/" + dto.getAccId(); // 리다이렉트 처리 부분
        }

        model.addAttribute("errorMessage", "리뷰를 추가하는 도중 오류가 발생했습니다. 다시 시도해주세요.");
        return "redirect:/error";
    }

    // 리뷰 삭제
    @PostMapping("/{accId}/delete/{reviewId}")
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
    @PostMapping("/{accId}/update/{reviewId}")
    public String postUpdateReview(@PathVariable("reviewId") long reviewId, @Valid @ModelAttribute("reviewDto") ReviewDto dto, Model model) {
        log.info("POST /accom/detail/" + dto.getAccId() + "/update/" + dto.getReviewId() + " dto " + dto + "reviewId" + reviewId);

        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            model.addAttribute("data", review.get());
            return "redirect:/accom/detail/" + dto.getAccId();
        } else {
            return "redirect:/error";
        }
    }
}