package com.sms.demo.service.impl;

import com.sms.demo.constants.AppConstants;
import com.sms.demo.dto.request.LoginRequest;
import com.sms.demo.dto.request.UserDto;
import com.sms.demo.dto.response.LoginResponse;
import com.sms.demo.entity.Role;
import com.sms.demo.entity.User;
import com.sms.demo.exception.handler.DemoAuthenticationException;
import com.sms.demo.repository.RoleRepository;
import com.sms.demo.repository.UserRepository;
import com.sms.demo.service.AuthenticationService;
import com.sms.demo.utils.CommonUtilities;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final Environment env;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, Environment env) {
        this.authenticationManager = authenticationManager;
        this.env = env;
    }

    @Override
    public String sendOtp(String username, String phoneNumber) {
        log.debug("Generating OTP for username: {}", username);
        String otp = CommonUtilities.generateOTP.apply(username);
        sendOtpAttempt(phoneNumber, otp);
        log.debug("OTP sent to phone number: {}", phoneNumber);
        return "OTP sent Successfully";
    }

    @Override
    public LoginResponse loginWithOtp(LoginRequest loginRequest) {
        LoginResponse loginResponse = null;
        String otpStoredForUser = CommonUtilities.otpStore.get(loginRequest.getUsername());


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getOtp());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

            if (env != null && otpStoredForUser.equals(loginRequest.getOtp())) {
                String secret = env.getProperty(AppConstants.JWT_SECRET_KEY,
                        AppConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer("My Demo App").subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime() + 30000000))
                        .signWith(secretKey).compact();
                loginResponse = LoginResponse.builder().status("success").token(jwt).build();
                CommonUtilities.removeOtpFromStore.accept(loginRequest.getUsername());
                return loginResponse;
            }
            throw new UsernameNotFoundException("Username not found");
        }
        throw new DemoAuthenticationException("Oops");
    }

    @Override
    public boolean isAccountAlreadyRegistered(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean isAccountAlreadyRegisteredWithRole(String username, String role) {
        return userRepository.findByUsernameAndRole(username, role).isPresent();
    }

    @Transactional
    @Override
    public UserDto registerNewUser(UserDto userDto, String roleName) {
        Set<Role> roles = new HashSet<>();

        Role existingRole = roleRepository.findByRoleName(roleName);
        if (existingRole == null) {
            existingRole = new Role();
            existingRole.setRoleName(roleName);
            existingRole = roleRepository.save(existingRole);
            log.info("Created new role: {}", existingRole);
        } else {
            log.info("Found existing role: {}", existingRole);
        }
        roles.add(existingRole);
        userDto.setRoles(roles);
        userDto.setEnabled(true);

        // Will have to change these and moved to mapper later.

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEnabled(userDto.getEnabled());
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
        return userDto;
    }

    private void sendOtpAttempt(String phoneNumber, String otp) {
        // Logic to integrate with third party to actually send the OTP
        // Meanwhile we will print it in the console
        log.debug("OTP send to mobile {} is {}", phoneNumber, otp);
        System.out.println("OTP send to mobile " + phoneNumber + " is " + otp);
    }
}
