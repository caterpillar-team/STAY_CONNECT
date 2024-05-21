package com.caterpillars.StayConnect.model;

import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
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
    @UniqueConstraint(name = "UK_name_address", columnNames = { "name", "address" })
})
public class Accommodation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @Column(nullable = false)
  private String address;
  @Column(nullable = false)
  private Double latitude;
  @Column(nullable = false)
  private Double longitude;

}
