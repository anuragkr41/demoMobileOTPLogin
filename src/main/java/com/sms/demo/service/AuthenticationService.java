package com.sms.demo.service;

import com.sms.demo.dto.request.UserDto;

public interface AuthenticationService {
     String sendOtp(String username, String phoneNumber);
     String loginWithOtp(String username, String otp);
     boolean isAccountAlreadyRegistered(String username);
     boolean isAccountAlreadyRegisteredWithRole(String username, String role);
     UserDto registerNewUser(UserDto userDto, String roleName);

}
