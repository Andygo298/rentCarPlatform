package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import com.github.andygo298.rentCarPlatform.service.UserService;
import com.github.andygo298.rentCarPlatform.web.rq.RegCreateRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    private SecurityService securityService;
    private UserService userService;

    public RegisterController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @GetMapping()
    public String register() {
        return "register";
    }

    @PostMapping()
    public String registerNew(ModelMap modelMap, RegCreateRq regCreateRq) {
        String login = regCreateRq.getLogin();
        String password = regCreateRq.getPassword();
        String firstName = regCreateRq.getFirstName();
        String lastName = regCreateRq.getLastName();
        String email = regCreateRq.getEmail();
        AuthUser authUser = securityService.login(login, password);
        if (authUser != null) {
            modelMap.addAttribute("errorExist", "This user already exists");
            return "register";
        }
        long userId = userService.saveUsers(new User(null, firstName, lastName, email, false));
        log.info("--- Auth info: firstName: {}, lastName: {}, email: {}, login: {}.", firstName, lastName, email, login);
        log.info("--- User created with id={} at {}.", userId, LocalDateTime.now());
        Long idAuthUser = securityService.saveAuthUser(
                new AuthUser(null, login, password, Role.USER, userService.getUserById(userId).getId())
        );
        modelMap.addAttribute("customMessage", "Thanks for registration.");
        modelMap.addAttribute("customMessage2", "You can Sign In using your login and password.");
        return "login";
    }

}
