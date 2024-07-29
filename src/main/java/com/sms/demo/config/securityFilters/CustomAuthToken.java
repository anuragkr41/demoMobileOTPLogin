package com.sms.demo.config.securityFilters;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomAuthToken extends UsernamePasswordAuthenticationToken {
    private final String username;
    public CustomAuthToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, authorities);
        this.username = username;
    }
}
