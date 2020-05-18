package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import com.github.andygo298.rentCarPlatform.service.UserService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultSecurityService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultUserService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RegisterServlet.class);
    private SecurityService securityService = DefaultSecurityService.getInstance();
    private UserService userService = DefaultUserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtils.forward("register", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        AuthUser authUser = securityService.login(login,password);
        if (authUser != null) {
            req.setAttribute("error","This user already exists");
            WebUtils.forward("/register", req, resp);
            return;
        }
        long userId = userService.saveUsers(new User(null, firstName, lastName, email, false));
        log.info("user created:{} at {}", userId, LocalDateTime.now());
        Long idAuthUser = securityService.saveAuthUser(new AuthUser(null, login, password, Role.USER, userService.getUserById(userId)));
        req.setAttribute("customMessage","Thanks for registration.");
        req.setAttribute("customMessage2","You can Sign In using your login and password.");
        WebUtils.forward("login", req, resp);

    }
}