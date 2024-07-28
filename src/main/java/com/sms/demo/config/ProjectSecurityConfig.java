package com.sms.demo.config;

import com.sms.demo.service.impl.PictuUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    private final PictuUserServiceImpl pictuUserService;


    public ProjectSecurityConfig(PictuUserServiceImpl pictuUserService) {
        this.pictuUserService = pictuUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable) // Disable CORS
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing purposes
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults -> {}); // Use HTTP Basic authentication
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//        auth.userDetailsService(pictuUserService).passwordEncoder(passwordEncoder());
//        return auth;
//    }
}
