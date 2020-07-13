package com.github.andygo298.rentCarPlatform.web;

import com.github.andygo298.rentCarPlatform.model.enums.Specialization;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


public class WebUtils {

    public static String readCookie(HttpServletRequest req , String key) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> key.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }

    public static Specialization getSpecEnum(String specialization){
       return Specialization.valueOf(specialization.toUpperCase());
    }
}
