package com.example.plusweek.service;

import com.example.plusweek.dto.UserLoginRequestDto;
import com.example.plusweek.dto.UserRequestDto;
import com.example.plusweek.entiry.User;
import com.example.plusweek.jwt.JwtUtil;
import com.example.plusweek.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void userSignup(UserRequestDto userRequestDto) {

        String username = userRequestDto.getUsername();
//        if(userRequestDto.getPassword().contains(username)){
//            throw new IllegalArgumentException("id를 포함할 수 없습니다.");
//        }
        String password = userRequestDto.getPassword();
        String checkPassword = userRequestDto.getCheckPassword();

        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }
        if(checkPassword.equals(password))
        {
            password = passwordEncoder.encode(password);

            User user = new User(username, password);
            userRepository.save(user);
        }
        else
        {
            throw new IllegalArgumentException("확인 비밀번호가 틀립니다.");
        }
    }
    public void login(UserLoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();


        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


}
