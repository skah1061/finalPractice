package com.example.plusweek.contoller;

import com.example.plusweek.dto.UserLoginRequestDto;
import com.example.plusweek.dto.UserRequestDto;
import com.example.plusweek.entiry.User;
import com.example.plusweek.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public String userSignup(@Valid @RequestBody UserRequestDto userRequestDto){


        userService.userSignup(userRequestDto);

        return "회원가입 완료";
    }
    @PostMapping("/user/login")
    public String userLogin(@RequestBody UserLoginRequestDto userRequestDto, HttpServletResponse response) throws UnsupportedEncodingException {
        userService.userLogin(userRequestDto, response);

        return "login완료";
    }
}
