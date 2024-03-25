package com.devsutest.demo.exception;

public class BadRequestExp extends RuntimeException{
    public BadRequestExp(String msg){
        super(msg);
    }
}
