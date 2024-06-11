package com.caterpillars.StayConnect.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "roomInfo_id")
  private RoomInfo roomInfo;

  private String title;

  private String contents;

  private LocalDateTime createdAt;
//  @PrePersist
//  protected void onCreate() {
//    this.createdAt = LocalDateTime.now();
//  }
  private int rate;
}
