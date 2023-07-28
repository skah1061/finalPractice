package com.example.plusweek.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    String username;
    String password;
}
