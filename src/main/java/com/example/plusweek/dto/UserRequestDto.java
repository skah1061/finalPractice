package com.example.plusweek.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$",
            message = "최소 3자 이상 20자 이하 알파벳 소문자(a~z), 대문자(A~Z), 숫자(0~9) 로 구성되어야 합니다.")
    String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "최소 4자 이상 20자 이하 알파벳 소문자(a~z), 대문자(A~Z), 숫자(0~9) 로 구성되어야 합니다.")
    String password;
    String checkPassword;

}
