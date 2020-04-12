package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.service.SecurityService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) {
        rq.getSession().removeAttribute("authUser");
        rq.getSession().invalidate();
        WebUtils.forward("/login", rq, rs);
    }

}
