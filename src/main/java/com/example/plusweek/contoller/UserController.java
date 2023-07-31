package com.example.plusweek.contoller;

import com.example.plusweek.dto.ApiResponseDto;
import com.example.plusweek.dto.UserLoginRequestDto;
import com.example.plusweek.dto.UserRequestDto;
import com.example.plusweek.entiry.User;
import com.example.plusweek.jwt.JwtUtil;
import com.example.plusweek.service.UserService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    JwtUtil  jwtUtil;
    public UserController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user/signup")
    public String userSignup(@Valid @RequestBody UserRequestDto userRequestDto){


        userService.userSignup(userRequestDto);

        return "회원가입 완료";
    }
    @PostMapping("/user/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody UserLoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto);

        // Cookie 에 넣기
        String token = jwtUtil.createToken(loginRequestDto.getUsername());
        jwtUtil.addJwtToCookie(token, response);

        return ResponseEntity.status(200).body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }

}
