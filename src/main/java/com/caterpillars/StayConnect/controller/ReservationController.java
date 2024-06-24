package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.dto.ReservationDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomInfoService roomInfoService;

    @Value("${PORTONE_API_KEY}")
    private String apiKey;

    @Value("${PORTONE_SECRET_KEY}")
    private String apiSecret;

    // 결제 성공
    @PostMapping("/paySuccess")
    public @ResponseBody ResponseEntity<String> paySuccess(@ModelAttribute PaymentDto paymentDto) {
        log.info(paymentDto.toString());

        // userId와 roomInfoId를 사용하여 User와 RoomInfo 조회
        User user = userService.findById(paymentDto.getUserId());
        RoomInfo roomInfo = roomInfoService.findById(paymentDto.getRoomInfoId());

        // Create and save reservation
        reservationService.createReservation(paymentDto.getImp_uid(), user, roomInfo, LocalDateTime.now(), LocalDateTime.now().plusDays(1), paymentDto);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }


    @GetMapping("/reservation/{reservationId}")
    public ReservationDto getReservationDetails(@PathVariable Long reservationId) {
        return reservationService.getReservationDetails(reservationId);
    }

    // 결제취소
    @PostMapping("/cancel_reservation")
    public Map<String, Object> cancelReservation(@RequestBody Map<String, String> request) {
        System.out.println("received request : delete");

        String reservationIdStr = request.get("reservationId");
        Long reservationId = Long.parseLong(reservationIdStr);
        Map<String, Object> response = new HashMap<>();

        try {
            log.info("Deleting reservation with ID: " + reservationId);
            reservationService.deleteReservationById(reservationId);
            response.put("success", true);
        } catch (Exception e) {
            log.info("Error while deleting reservation: " + e.getMessage());
            response.put("success", false);
            response.put("message", "예약 삭제 중 오류 발생: " + e.getMessage());
        }
        return response;
    }


}
