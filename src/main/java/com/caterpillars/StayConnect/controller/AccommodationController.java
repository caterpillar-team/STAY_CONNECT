package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/detail/{id}")
    public String accom_detail(@PathVariable long id, Authentication authentication, Model model) {

        System.out.println(authentication);
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        System.out.println(user.get().getPhoneNumber());

        Optional<RoomInfo> result = roomInfoRepository.findById(id);
        if (result.isPresent()) {
            RoomInfo roomInfo = result.get();
            if (roomInfo.getAccommodation() != null) {
                List<Review> reviews = reviewRepository.findByRoomInfo(roomInfo);
                model.addAttribute("accom", roomInfo);
                model.addAttribute("reviews", reviews);
                model.addAttribute("phoneNumber", user.get().getPhoneNumber());
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
