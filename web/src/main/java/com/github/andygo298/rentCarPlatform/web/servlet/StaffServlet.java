package com.github.andygo298.rentCarPlatform.web.servlet;


import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/staff")
public class StaffServlet extends HttpServlet {
    private StaffService staffService = DefaultStaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageStaff = req.getParameter("page") != null
                ? Integer.parseInt(req.getParameter("page"))
                : 1;

        double countRecordsFromStaff = staffService.getCountRecordsFromStaff();
        int countPagesStaff = ServiceUtil.getCountPages(countRecordsFromStaff);

//        Cookie currentPage = new Cookie("currentPageStaff", Integer.toString(page));
//        currentPage.setMaxAge(-1);
//        resp.addCookie(currentPage);
        List<Staff> staffList = staffService.getStaff(pageStaff);

        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("staff", staffList);
        servletContext.setAttribute("countPagesStaff", countPagesStaff);
        servletContext.setAttribute("currentPageStaff", pageStaff);

//        req.setAttribute("staff", staffList);
//        req.setAttribute("countPagesStaff", countPagesStaff);
//        req.setAttribute("currentPageStaff", pageStaff);


        WebUtils.forward("staff", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
