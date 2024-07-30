//package com.sms.demo.config.securityFilters;
//
//import io.micrometer.common.util.StringUtils;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.text.ParseException;
//
//@Component
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Order(1)
//public class JwtTokenFilter extends OncePerRequestFilter {
//    private final String username;
//    private final String otp;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = extractJwtToken(request);
//        try{
//            if(StringUtils.isNotEmpty(token)){
//                processJwtToken(token);
//            }
//        }catch (ParseException | RuntimeException){
//
//            throw new RuntimeException("Handle Token Validation Exception");
//        }
//
//
//    }
//
//    private String extractJwtToken(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        if(bearerToken!=null && bearerToken.startsWith("Bearer")){
//            return bearerToken.substring(7);
//        }
//        return null;
//
//    }
//
//    private String getUserNameFromToken(String token){
//        //Write the logic here
//        return "";
//    }
//}
