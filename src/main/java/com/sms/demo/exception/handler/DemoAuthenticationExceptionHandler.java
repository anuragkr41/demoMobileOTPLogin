package com.sms.demo.exception.handler;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DemoAuthenticationExceptionHandler {
    @ExceptionHandler(DemoAuthenticationException.class)
    public ResponseEntity<String> wrongOTPException(DemoAuthenticationException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Incorrect OTP Entered");

    }

}
