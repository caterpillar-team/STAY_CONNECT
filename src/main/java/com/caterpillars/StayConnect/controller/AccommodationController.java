package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/detail/{id}")
    public String accom_detail(@PathVariable long id, Model model) {
        Optional<RoomInfo> result = roomInfoRepository.findById(id);
        if (result.isPresent()) {
            RoomInfo roomInfo = result.get();
            if (roomInfo.getAccommodation() != null) {
                List<Review> reviews = reviewRepository.findByRoomInfo(roomInfo);
                model.addAttribute("accom", roomInfo);
                model.addAttribute("reviews", reviews);
                return "pages/accom_detail";
            } else {
                // Accommodation 객체가 null인 경우 처리
                return "redirect:/error";
            }
        } else {
            return "redirect:/";
        }
    }
}
