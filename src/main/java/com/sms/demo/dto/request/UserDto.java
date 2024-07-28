package com.sms.demo.dto.request;

import com.sms.demo.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private Set<Role> roles = new HashSet<>();
    private Boolean enabled;
}
