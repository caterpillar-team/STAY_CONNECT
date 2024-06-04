package com.caterpillars.StayConnect.model.entities;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Accommodation accommodation;

  @Column(nullable = false)
  private String roomType;

  @Column(nullable = false)
  private int count;

  @Column(nullable = false)
  private int numberOfPeople;

  @Column(nullable = false)
  private int price;

  @OneToMany(mappedBy = "roomInfo")
  private List<Review> reviews;

  @OneToMany(mappedBy = "roomInfo")
  private List<Reservation> reservations;
}
