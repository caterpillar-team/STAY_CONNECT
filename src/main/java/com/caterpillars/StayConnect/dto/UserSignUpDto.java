package com.caterpillars.StayConnect.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private String realName;
    private LocalDate birth;
    private String gender;
    private String phoneNumber;
    private String username;
    private String password;
    private String confirmPassword;
}
