package com.caterpillars.StayConnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationGrade {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String grade;
}
