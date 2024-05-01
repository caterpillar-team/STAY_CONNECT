package com.caterpillars.StayConnect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @Entity
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String realName;
    private LocalDate birth;
    private String gender;
    private String phoneNumber;
    private String username;
    private String password;
}