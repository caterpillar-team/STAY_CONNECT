package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.RoomInfoService;
import com.caterpillars.StayConnect.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/paySuccess")
    public @ResponseBody void paySuccess(@ModelAttribute PaymentDto paymentDto) {
        log.info(paymentDto.toString());
    }

    @PostMapping("/createResv")
    public Reservation createReservation(@RequestParam String imp_uid,
                                         @RequestParam Long userId,
                                         @RequestParam Long roomInfoId,
                                         @RequestParam String checkIn,
                                         @RequestParam String checkOut
    ) {
        User user = userService.findById(userId);
        RoomInfo roomInfo = roomInfoService.findById(roomInfoId);
        LocalDateTime checkInDate = LocalDateTime.parse(checkIn);
        LocalDateTime checkOutDate = LocalDateTime.parse(checkOut);


        return reservationService.createReservation(imp_uid, user, roomInfo, checkInDate, checkOutDate);
    }

}
