package com.sms.demo.dto.request;

import com.sms.demo.entity.Role;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto extends UserDto{


    private String countryCode;
    private String phoneNumber;
    public UserRegistrationDto(String username, Set<Role> roles, boolean enabled, String countryCode, String phoneNumber) {
        super(username, roles, enabled);
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
    }

}
