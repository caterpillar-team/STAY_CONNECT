package com.caterpillars.StayConnect.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private String realName;
    private LocalDate birth;
    private String gender;
    private String phoneNumber;
    private String username;
    private String password;
    private String confirmPassword;
}
