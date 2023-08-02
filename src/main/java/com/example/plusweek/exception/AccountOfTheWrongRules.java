package com.example.plusweek.exception;

public class AccountOfTheWrongRules extends RuntimeException{
    public AccountOfTheWrongRules(String message){
        super(message);
    }
}