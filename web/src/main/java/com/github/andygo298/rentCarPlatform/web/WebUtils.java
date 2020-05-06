package com.github.andygo298.rentCarPlatform.web;

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

    public static void forward(String page, HttpServletRequest rq, HttpServletResponse rs) {
        try {
            rq.getRequestDispatcher("/" + page + ".jsp").forward(rq, rs);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void redirect(String page, HttpServletRequest rq, HttpServletResponse rs) {
        try {
            rs.sendRedirect(rq.getContextPath() + page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
