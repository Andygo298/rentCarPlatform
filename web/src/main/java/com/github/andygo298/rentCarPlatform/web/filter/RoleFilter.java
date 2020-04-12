package com.github.andygo298.rentCarPlatform.web.filter;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/addNewCar", "/deleteCar","/approveOrder","/editCar","/rejectOrder"})
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        AuthUser authUser = (AuthUser) rq.getSession().getAttribute("authUser");
        if (!authUser.getRole().name().equals("ADMIN")) {
            WebUtils.redirect("/homepage", rq, ((HttpServletResponse) servletResponse));
            return;
        }
        filterChain.doFilter(rq, servletResponse);
    }
}
