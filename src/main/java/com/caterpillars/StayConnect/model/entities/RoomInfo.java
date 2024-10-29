package com.caterpillars.StayConnect.model.entities;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private String bedType;

    @Column(nullable = false)
    private String stayType;

    @Column(nullable = false)
    private int numberOfPeopleStandard;

    @Column(nullable = false)
    private int numberOfPeopleMax;

    @Column(nullable = false)
    private String checkInTime;

    @Column(nullable = false)
    private String checkOutTime;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

    @OneToMany(mappedBy = "roomInfo", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Review> reviews;

    @OneToMany(mappedBy = "roomInfo", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Reservation> reservations;
}
