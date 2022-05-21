package com.bd.webdiary.util;

import com.bd.webdiary.service.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static UserPrinciple getUser(){
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
