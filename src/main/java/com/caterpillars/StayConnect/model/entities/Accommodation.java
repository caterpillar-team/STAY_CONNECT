package com.caterpillars.StayConnect.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accommodation", uniqueConstraints = {
        @UniqueConstraint(name = "UK_name_address", columnNames = {"name", "address"})
}, indexes = {
        @Index(name = "fulltext_index", columnList = "name")
})
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "grade")
    private Grade grade;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "accommodation")
    private List<RoomInfo> roomInfos;

}
