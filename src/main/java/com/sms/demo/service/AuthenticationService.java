package com.sms.demo.service;

import com.sms.demo.dto.request.LoginRequest;
import com.sms.demo.dto.request.UserDto;
import com.sms.demo.dto.response.LoginResponse;

public interface AuthenticationService {
     String sendOtp(String username, String phoneNumber);
     LoginResponse loginWithOtp(LoginRequest loginRequest);
     boolean isAccountAlreadyRegistered(String username);
     boolean isAccountAlreadyRegisteredWithRole(String username, String role);
     UserDto registerNewUser(UserDto userDto, String roleName);

}
