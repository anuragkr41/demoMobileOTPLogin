package com.sms.demo.controller;

import com.sms.demo.dto.request.LoginRequest;
import com.sms.demo.dto.request.PreLoginRequest;
import com.sms.demo.dto.request.UserDto;
import com.sms.demo.dto.request.UserRegistrationDto;
import com.sms.demo.entity.User;
import com.sms.demo.repository.UserRepository;
import com.sms.demo.service.AuthenticationService;
import com.sms.demo.service.PictuUserService;
import com.sms.demo.utils.CommonUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("public/api/v1/auth")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PictuUserService pictuUserService;

    @Autowired
    AuthenticationService authenticationService;

    /*
    For register or for login it will use the same endpoint
    First it will try to find if the username is there in the database or not.
    If not then it will
    * */

    @PostMapping("/preLogin")
    public ResponseEntity<String> preLogin(@RequestBody PreLoginRequest dto) {

        String username = CommonUtilities.generateUsernameFromCountryCodeAndPhoneNo
                .apply(dto.getCountryCode(), dto.getPhoneNumber());
        String phoneNumber = CommonUtilities.getPhoneNumberWithCountryCode
                .apply(dto.getCountryCode(), dto.getPhoneNumber());

        //Check whether an account exist already for the username with the role as mentioned in dto

        boolean isRegistered = authenticationService.isAccountAlreadyRegistered(username);
        String response="";

        if (isRegistered) {
            response = authenticationService.sendOtp(username, phoneNumber);
        } else {
            log.debug("Trying to register new user");
        }

        return ResponseEntity.ok(response); // This line is also to be modified later

//        try {
//            User user = new User();
//            user.setUsername(dto.getUsername());
//            user.setEnabled(dto.isEnabled());
//            user.setRole(dto.getRole());
//            userRepository.save(user);
//            return ResponseEntity.ok().body("User is saved");
//        }catch (Exception e){
//            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception Occured"+e.getMessage());
//        }
//        return ResponseEntity.ok("Process Completed");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.loginWithOtp(loginRequest.getUsername(), loginRequest.getOtp());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register/{userType}")
    public ResponseEntity<UserDto> registerUser(
            @RequestBody UserRegistrationDto userRegistrationDto, @PathVariable String userType){

        String username = CommonUtilities.generateUsernameFromCountryCodeAndPhoneNo
                .apply(userRegistrationDto.getCountryCode(), userRegistrationDto.getPhoneNumber());

        boolean isRegistered = authenticationService.isAccountAlreadyRegistered(username);
        if(isRegistered){
            throw new RuntimeException("Account already registered.");
        }
//        UserDto dto = new UserDto();
        userRegistrationDto.setUsername(username);

//        dto.setUsername(userRegistrationDto.getUsername());
        UserDto userDto = authenticationService.registerNewUser(userRegistrationDto, userType);

        return ResponseEntity.ok(userDto);
    }
}
