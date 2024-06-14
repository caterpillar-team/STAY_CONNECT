package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
@Slf4j
public class ReservationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/paySuccess")
    public @ResponseBody void paySuccess(@ModelAttribute PaymentDto paymentDto) {
        log.info(paymentDto.toString());
    }


//    @PostMapping("/create")
//    public Reservation createReservation(@RequestParam String imp_uid, @RequestParam Long userId, @RequestParam Long roomInfoId, @RequestParam String checkIn, @RequestParam String checkOut) {
//        Long user = userService.findById(userId); // userService를 통해 사용자 정보를 가져옴
//        RoomInfo roomInfo = roomInfoService.findById(roomInfoId); // roomInfoService를 통해 객실 정보를 가져옴
//        LocalDateTime checkInDate = LocalDateTime.parse(checkIn);
//        LocalDateTime checkOutDate = LocalDateTime.parse(checkOut);
//
//        return reservationService.createReservation(imp_uid, user, roomInfo, checkInDate, checkOutDate);
//    }
}
