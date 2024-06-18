package com.caterpillars.StayConnect.model.entities;

import java.util.List;

import groovy.util.logging.Slf4j;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RoomInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;

  private String roomType;

  private String bedType;

  private String stayType;

  private int numberOfPeopleStandard;

  private int numberOfPeopleMax;

  private String checkInTime;

  private String checkOutTime;

  private int count;

  private int price;

  @OneToMany(mappedBy = "roomInfo")
  private List<Review> reviews;

  @OneToMany(mappedBy = "roomInfo")
  private List<Reservation> reservations;
}
