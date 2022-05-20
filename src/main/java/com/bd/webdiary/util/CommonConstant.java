package com.bd.webdiary.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonConstant {

    public static final int STATUS_ID_ACTIVE = 1;
    public static final int STATUS_ID_INACTIVE = 2;

    public static final Integer PASSWORD_STRENGTH = 10;
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(PASSWORD_STRENGTH);
}
