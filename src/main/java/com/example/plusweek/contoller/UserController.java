package com.example.plusweek.contoller;

import com.example.plusweek.exception.AccountOfTheWrongRules;
import com.example.plusweek.exception.ApiResponseDto;
import com.example.plusweek.dto.UserLoginRequestDto;
import com.example.plusweek.dto.UserRequestDto;
import com.example.plusweek.jwt.JwtUtil;
import com.example.plusweek.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    MessageSource messageSource;
    JwtUtil  jwtUtil;
    public UserController(UserService userService, JwtUtil jwtUtil,MessageSource messageSource){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }

    @PostMapping("/user/signup")
    public String userSignup(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ApiResponseDto apiResponseDto = new ApiResponseDto("계정생성", 200);
        if(fieldErrors.size() > 0){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                throw new AccountOfTheWrongRules(messageSource.getMessage(
                        "acount.state.error",
                        null,
                        "Wrong Acount Rule",
                        Locale.getDefault()
                )
                );
//                apiResponseDto = new ApiResponseDto("계정규칙이 올바르지 않습니다.", 400);
            }

        }

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
