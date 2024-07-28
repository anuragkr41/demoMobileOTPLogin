package com.sms.demo.service.impl;

import com.sms.demo.config.customUserDetails.CustomerDetails;
import com.sms.demo.entity.User;
import com.sms.demo.repository.RoleRepository;
import com.sms.demo.repository.UserRepository;
import com.sms.demo.service.PictuUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictuUserServiceImpl implements PictuUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return CustomerDetails.fromUserEntityToCustomUserDetails(user);
    }
}
