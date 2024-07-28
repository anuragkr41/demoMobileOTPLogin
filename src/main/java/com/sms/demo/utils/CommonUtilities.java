package com.sms.demo.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommonUtilities {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_LENGTH = 6;
    public static Map<String, String> otpStore = new HashMap<>();


    public static final BiFunction<String, String, String> generateUsernameFromCountryCodeAndPhoneNo
            = (countryCode, phoneNo) -> countryCode + "-" + phoneNo;
    public static final BiFunction<String, String, String> getPhoneNumberWithCountryCode
            = (countryCode, phoneNo) -> countryCode + phoneNo;
    public static final BiFunction<String, String, String> getPhoneNumberWithOutCountryCode
            = (countryCode, phoneNo) -> phoneNo;

    public static final Function<String, String> generateOTP = (username) -> {
        int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
        String otpString = String.format("%06d", otp);
        otpStore.put(username, otpString);
        return otpString;
    };

    public static final Consumer<String> removeOtpFromStore
            = username -> otpStore.remove(username);
}
