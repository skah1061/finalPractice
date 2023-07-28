package com.example.plusweek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //Timestamped 시간을 체크해주는 기능을 사용하기위한 애너테이션

public class PlusWeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusWeekApplication.class, args);
    }

}
