package com.caterpillars.StayConnect.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "room_info_id", nullable = false)
  private RoomInfo roomInfo;

  private LocalDateTime checkIn;
  private LocalDateTime checkOut;

  private LocalDateTime reservationAt;

  private String imp_uid;
  private int price;
  private String pay_method;
  private String merchant_uid;

  private String reservationType;
}