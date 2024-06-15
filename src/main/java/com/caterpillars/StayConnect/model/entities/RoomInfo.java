package com.caterpillars.StayConnect.model.entities;

import groovy.util.logging.Slf4j;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
