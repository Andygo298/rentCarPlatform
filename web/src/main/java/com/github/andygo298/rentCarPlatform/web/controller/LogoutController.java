package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logout-user")
public class LogoutController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping()
    public String doGet(HttpServletRequest rq) {
        log.info("---User: {} was logout.", (
                (AuthUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                .getLogin()
        );
        SecurityContextHolder.clearContext();
        try {
            rq.logout();
        } catch (ServletException e) {
            log.error("---User wasn't logout. ERROR servlet exception.");
            throw new RuntimeException();
        }

        return "redirect:/login";
    }
}
