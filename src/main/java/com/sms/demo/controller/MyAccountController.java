package com.sms.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
public class MyAccountController {
    @GetMapping("/balance")
    public String getAccountBalance(){
        return "Your current balance is Rs 19,00,00,000";
    }
}
