package com.caterpillars.StayConnect.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.service.ReviewService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/accommodation/detail")
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{accId}/addReview")
    public String postAddReview(@PathVariable("accId") Long accId, @Valid @ModelAttribute("reviewDto") ReviewDto dto,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값에 오류가 있습니다. 다시 시도해주세요.");
            return null;
        }

        RoomInfo roomInfo = reviewService.findRoomInfoById(dto.getRoomInfoId());

        if (roomInfo == null) {
            model.addAttribute("errorMessage", "해당 숙소 정보를 찾을 수 없습니다.");
            return null;
        }

        boolean isAdd = reviewService.addReview(dto, roomInfo);

        if (isAdd) {
            return "redirect:/accommodation/detail/" + accId;
        }

        model.addAttribute("errorMessage", "리뷰를 추가하는 도중 오류가 발생했습니다. 다시 시도해주세요.");
        return null;
    }

    @DeleteMapping("/{accId}/delete/{reviewId}")
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("reviewId") Long reviewId,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> response = new HashMap<>();

        try {
            ReviewDto dto = reviewService.getReviewDto(reviewId);
            if (dto == null) {
                response.put("message", "리뷰를 찾을 수 없습니다.");
            }

            boolean isDelete = reviewService.deleteReview(reviewId);

            if (isDelete) {
                response.put("message", "리뷰가 성공적으로 삭제되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "리뷰 삭제에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("message", "리뷰 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/update/review")
    public @ResponseBody ResponseEntity<Map<String, Object>> updateReview(@ModelAttribute ReviewDto reviewDto) {
        Map<String, Object> result = reviewService.updateReview(reviewDto);

        if (result.get("message") == null)
            result.put("message", "리뷰 업데이트 성공!");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}