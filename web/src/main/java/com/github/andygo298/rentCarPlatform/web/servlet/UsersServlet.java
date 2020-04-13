package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.UserService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultUserService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UsersServlet.class);
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) {
        List<User> users = userService.getUsers();
        rq.setAttribute("users", users);
        WebUtils.forward("users", rq, rs);
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        String firstName = rq.getParameter("firstName");
        String lastName = rq.getParameter("lastName");
        String email = rq.getParameter("email");
        long userId = userService.saveUsers(new User(null, firstName, lastName, email, false));
//        log.info("user created:{} at {}", userId, LocalDateTime.now());
        userService.saveAuthUser(new AuthUser(null, login, password, Role.USER, userId));
        WebUtils.redirect("/users", rq, rs);
    }
}
