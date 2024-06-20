package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.service.PortOnePaymentService;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/user/accom")
@Slf4j
public class AccommodationController {

    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PortOnePaymentService portOnePaymentService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/detail/{id}")
    public String accom_detail(@PathVariable long id, Authentication authentication, Model model) {

        System.out.println(authentication);
        Optional<User> user = userService.findByUsername(authentication.getName());
        System.out.println(user.get().getPhoneNumber());

        Optional<RoomInfo> result = Optional.ofNullable(RoomInfoService.findById(id));
        if (result.isPresent()) {
            RoomInfo roomInfo = result.get();
            if (roomInfo.getAccommodation() != null) {
                List<Review> reviews = reviewRepository.findByRoomInfo(roomInfo);
                model.addAttribute("accom", roomInfo);
                model.addAttribute("reviews", reviews);
                model.addAttribute("phoneNumber", user.get().getPhoneNumber());
                model.addAttribute("userId", user.get().getId());
                model.addAttribute("roomInfoId", roomInfo.getId());
                return "pages/user/accom_detail";
            } else {
                // Accommodation 객체가 null인 경우 처리
                return "redirect:/error";
            }
        } else {
            return "redirect:/";
        }
    }

//    @GetMapping("/paySuccess")
//    public String paySuccess(@RequestParam boolean success, @RequestParam String imp_uid, @RequestParam int price, Authentication authentication, Model model) {
//        if (success) {
//            try {
//                PaymentDto payment = portOnePaymentService.getPaymentDetails(imp_uid);
//                if (payment != null) {
//                    Optional<User> user = userService.findByUsername(authentication.getName());
//                    Optional<RoomInfo> roomInfo = Optional.ofNullable(roomInfoService.findById(Long.valueOf(payment.getMerchant_uid())));
//
//                    if (user.isPresent() && roomInfo.isPresent()) {
//                        Reservation reservation = Reservation.builder()
//                                .user(user.get())
//                                .roomInfo(roomInfo.get())
//                                .checkIn(LocalDateTime.now())
//                                .checkOut(LocalDateTime.now().plusDays(1)) // 임시로 1박 설정
//                                .reservationAt(LocalDateTime.now())
//                                .price(price)
//                                .pay_method(payment.getPay_method())
//                                .reservationType("ONLINE")
//                                .build();
//
//                        reservationService.createReservation(reservation);
//                        return "pages/user/myPage";
//                    }
//                }
//            } catch (RuntimeException e) {
//                log.error("Payment error: " + e.getMessage());
//                return "결제에 실패했습니다.";
//            }
//        }
//        return "결제에 실패했습니다.";
//    }
}

