package com.caterpillars.StayConnect.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
@RestController
public class ReservationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomInfoService roomInfoService;

    @PostMapping("/paySuccess")
    public ResponseEntity<String> paySuccess(@ModelAttribute PaymentDto paymentDto) {

        log.info(paymentDto.toString());

        // userId와 roomInfoId를 사용하여 User와 RoomInfo 조회
        User user = userService.findById(paymentDto.getUserId());
        RoomInfo roomInfo = roomInfoService.findById(paymentDto.getRoomInfoId());

        // Create and save reservation
        reservationService.createReservation(paymentDto.getImp_uid(), user, roomInfo, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), paymentDto);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}