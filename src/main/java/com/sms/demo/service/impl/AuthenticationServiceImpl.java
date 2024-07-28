package com.sms.demo.service.impl;

import com.sms.demo.dto.request.UserDto;
import com.sms.demo.dto.request.UserRegistrationDto;
import com.sms.demo.dto.response.SuccessfulLoginResponse;
import com.sms.demo.entity.Role;
import com.sms.demo.entity.User;
import com.sms.demo.repository.RoleRepository;
import com.sms.demo.repository.UserRepository;
import com.sms.demo.service.AuthenticationService;
import com.sms.demo.utils.CommonUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String sendOtp(String username, String phoneNumber) {
        String otp = CommonUtilities.generateOTP.apply(username);
        sendOtpAttempt(phoneNumber, otp);
        return "OTP sent Successfully";
    }

    @Override
    public String loginWithOtp(String username, String otp) {
        String otpStoredForUser = CommonUtilities.otpStore.get(username);
        if(otpStoredForUser.equals(otp)) {
            return "eygsdfgsdfgsdfg.sdfgsdfgsdfghanefdhsdfh.sdfgqergwbtrh";
        }
        return "Invalid OTP";
    }

    @Override
    public boolean isAccountAlreadyRegistered(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    @Override
    public UserDto registerNewUser(UserDto userDto, String userType) {
        //inject default role -> user
        Set<Role> roles = new HashSet<>();
        userType = userType.toUpperCase();
        String roleName = "ROLES_" + userType;

        Role existingRole = roleRepository.findByRoleName(roleName);
        if (existingRole != null) {
            roles.add(existingRole);
        }

        userDto.setRoles(roles);
        userDto.setEnabled(true);

        //Will have to change these and moved to mapper later.

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEnabled(userDto.getEnabled());
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
        return userDto;
    }

    private void sendOtpAttempt(String phoneNumber, String otp) {
        //Logic to integrate with thrid party to actually send the OTP
        //Meanwhile we will print it in the console
        log.debug("OTP send to mobile {} is {}", phoneNumber, otp);
        System.out.println("OTP send to mobile " + phoneNumber + " is " + otp);

    }
}
