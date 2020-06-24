package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping({"/login", "/"})
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final SecurityService securityService;

    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping()
    public String login() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
            return "login";
        }
        return "redirect:/home";
    }

    @PostMapping()
    public String login(@RequestParam String login, @RequestParam String password, ModelMap modelMap) {
        AuthUser user = securityService.login(login, password);
        if (user == null) {
            modelMap.addAttribute("error", "login or password invalid");
            return "login";
        }
        log.info("---Authenicated as : {}.", user.getLogin());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/home";
    }

    private List<GrantedAuthority> getAuthorities(AuthUser authUser) {
        return Collections.singletonList((GrantedAuthority) () -> "ROLE_" + authUser.getRole().name());
    }
}
