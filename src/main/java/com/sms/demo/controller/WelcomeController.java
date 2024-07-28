package com.sms.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/home")
public class WelcomeController {

    @GetMapping("/welcome")
    public String getWelcomeMessage(){
        return "Welcome bro";
    }
    @GetMapping("/register")
    public String getRegisterMessage(){
        return "Register here";
    }
    @GetMapping("/login")
    public String getLoginMessage(){
        return "Login Here";
    }
}
