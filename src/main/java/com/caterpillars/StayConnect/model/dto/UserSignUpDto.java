package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDate;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {

    private String realName;
    private LocalDate birth;
    private Boolean gender;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
