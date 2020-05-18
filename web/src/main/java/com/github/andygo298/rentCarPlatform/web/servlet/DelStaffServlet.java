package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delStaff")
public class DelStaffServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DelStaffServlet.class);
    private StaffService staffService = DefaultStaffService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        Integer currentPageStaff = (Integer) servletContext.getAttribute("currentPageStaff");

        Long delStaffId = Long.parseLong(req.getParameter("id"));
        staffService.delStaff(delStaffId);
        log.info("delete Staff with id=  {} logged", delStaffId.toString());

        WebUtils.redirect("/staff?page=" + currentPageStaff, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
