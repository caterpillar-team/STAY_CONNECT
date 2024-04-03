package com.caterpillars.StayConnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserSignUpDto {
    @NotBlank(message = "실명은 필수입니다.")
    private String realName;
//    @NotBlank(message = "생년월일은 필수입니다.")
//    @Past()
    private LocalDate birth;
//    @NotBlank(message = "성별은 필수입니다.")
    private String gender;
//    @NotBlank(message = "연락처는 필수입니다.")
    private String phoneNumber;
//    @NotBlank(message = "사용자 이름은 필수입니다.")
    private String username;
//    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
//    @NotBlank(message = "재확인 비밀번호는 필수입니다.")
    private String confirmPassword;
}
