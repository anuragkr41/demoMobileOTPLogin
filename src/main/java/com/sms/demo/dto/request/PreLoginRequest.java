package com.sms.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreLoginRequest {
    private String countryCode;
    private String phoneNumber; // Validations to be done later
//    private String role
//    Role is to be added later, this will decide whether it is photographer logging in or customer logging in

}
