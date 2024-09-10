package com.caterpillars.StayConnect.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.dto.ReservationDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
// @RestController
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomInfoService roomInfoService;

    // @Autowired
    // private PortOnePaymentService portOnePaymentService;

    // private PortOneTokenResponse portOneTokenResponse;

    @Value("${PORTONE_API_KEY}")
    private String apiKey;

    @Value("${PORTONE_SECRET_KEY}")
    private String apiSecret;

    // 결제 성공
    @PostMapping("/paySuccess")
    public @ResponseBody ResponseEntity<String> paySuccess(@ModelAttribute PaymentDto paymentDto) {

        // userId와 roomInfoId를 사용하여 User와 RoomInfo 조회
        User user = userService.findById(paymentDto.getUserId());
        RoomInfo roomInfo = roomInfoService.findById(paymentDto.getRoomInfoId());

        // Create and save reservation
        reservationService.createReservation(paymentDto.getImp_uid(), user, roomInfo, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), paymentDto);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/reservation/{reservationId}")
    public ReservationDto getReservationDetails(@PathVariable Long reservationId) {
        return reservationService.getReservationDetails(reservationId);
    }

    @GetMapping("/cancel_reservation")
    public String cancel(@RequestParam("reservationId") Long reservationId, Model model) {

        boolean result = reservationService.cancel_reservation(reservationId);

        if (result) {
            return "redirect:/user/myPage"; // 성공 시 리다이렉트
        } else {
            model.addAttribute("cancelResult", false);
            return "redirect:/user/errorPage"; // 실패 시 에러 페이지로 리다이렉트 (에러 페이지가 있는 경우)
        }
    }

    // ---------------------------
    // AccessToken 발급 Class
    // ---------------------------
    @Data
    private static class TokenResponse {
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse {
        public int code;
        public Object message;
        public TokenResponse response;
    }

    // -----------------------------
    // 인증정보 가져오기 Class
    // -----------------------------
    @Data
    private static class AuthInfoResponse {
        public int birth;
        public String birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public String origin;
        public String pg_provider;
        public String pg_tid;
        public String phone;
        public Object unique_in_site;
        public String unique_key;
    }

    @Data
    private static class PortOneAuthInfoResponse {
        public int code;
        public Object message;
        public AuthInfoResponse response;
    }

}
