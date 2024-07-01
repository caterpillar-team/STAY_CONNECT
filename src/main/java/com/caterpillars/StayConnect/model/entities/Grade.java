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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade;

    @OneToMany(mappedBy = "grade")
    private List<Accommodation> accommodation;
}