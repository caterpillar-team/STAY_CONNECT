package com.caterpillars.StayConnect.model.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "accommodation", uniqueConstraints = {
                @UniqueConstraint(name = "UK_name_address", columnNames = { "name", "address" }) }, indexes = {
                                @Index(name = "fulltext_index", columnList = "name") })
public class Accommodation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "grade", nullable = false)
        private Grade grade;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private Double latitude;

        @Column(nullable = false)
        private Double longitude;

        @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
        private List<RoomInfo> roomInfos;
}