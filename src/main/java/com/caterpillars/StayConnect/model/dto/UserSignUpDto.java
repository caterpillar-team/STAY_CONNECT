package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",message="'숫자','문자','특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 16자'까지 허용됩니다" )
    private String password;
    private String confirmPassword;
}
