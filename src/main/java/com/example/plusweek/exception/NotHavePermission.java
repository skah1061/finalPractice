package com.example.plusweek.exception;

public class NotHavePermission extends RuntimeException {
    public NotHavePermission(String msg) {
        super(msg);
    }
}
