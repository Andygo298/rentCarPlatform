package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.SecurityService;
import com.github.andygo298.rentCarPlatform.service.UserService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultSecurityService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultUserService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    private SecurityService securityService = DefaultSecurityService.getInstance();
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) {
        Object authUser = rq.getSession().getAttribute("authUser");
        if (authUser == null) {
            WebUtils.forward("login", rq, rs);
            return;
        }
        WebUtils.redirect("/homepage", rq, rs);
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        AuthUser user = securityService.login(login, password);
        if (user == null) {
            rq.setAttribute("error", "Login or password invalid");
            WebUtils.forward("/login", rq, rs);
            return;
        }
        log.info("user {} logged", user.getLogin());
        User currentUser = userService.getUserById(user.getUserId());
        rq.getSession().setAttribute("authUser", user);
        rq.getSession().setAttribute("activeUser", currentUser);
        WebUtils.redirect("/homepage", rq, rs);
    }
}
