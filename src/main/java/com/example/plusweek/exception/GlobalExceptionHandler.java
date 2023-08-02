package com.example.plusweek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccountOfTheWrongRules.class})           //회원가입시 중복된 사용자 존재
    public ResponseEntity<ApiResponseDto> handleException(AccountOfTheWrongRules ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                apiResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler({NotHavePermission.class})            //수정삭제 권한이 존재하지 않을 때
    public ResponseEntity<ApiResponseDto> handleException(NotHavePermission ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(
                // HTTP body
                apiResponseDto,
                // HTTP status code
                HttpStatus.FORBIDDEN
        );
    }
    @ExceptionHandler({ProblemToken.class})             //토큰에 문제가 있을 때
    public ResponseEntity<ApiResponseDto> handleException(ProblemToken ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(
                // HTTP body
                apiResponseDto,
                // HTTP status code
                HttpStatus.FORBIDDEN
        );
    }
    @ExceptionHandler({NotFoundException.class})             //토큰에 문제가 있을 때
    public ResponseEntity<ApiResponseDto> handleException(NotFoundException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                apiResponseDto,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }
}