package com.sms.demo.config.customUserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerDetails implements UserDetails {
    private final String username;
    private Collection<? extends GrantedAuthority> authorities;
    private final Boolean enabled;


    public CustomerDetails(String username, Collection<? extends GrantedAuthority> authorities, Boolean enabled) {
        this.username = username;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static CustomerDetails fromUserEntityToCustomUserDetails(com.sms.demo.entity.User user) {
        return new CustomerDetails(
                user.getUsername(),
                user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()),
                user.getEnabled()
        );
    }
}
