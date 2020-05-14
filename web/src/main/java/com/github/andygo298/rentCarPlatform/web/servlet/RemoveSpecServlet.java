package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/removeSpec")
public class RemoveSpecServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RemoveSpecServlet.class);
    private StaffService staffService = DefaultStaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = WebUtils.readCookie(req, "currentPageMaintenance");
        if (currentPage == null) currentPage ="1";

        Long remCarId = Long.valueOf(req.getParameter("remCarId"));
        Long remSpecId = Long.valueOf(req.getParameter("remSpecId"));

        staffService.removeStaffFromCar(remCarId, remSpecId);
        log.warn("staff id= {} was deleted from car id= {}", remCarId.toString(), remSpecId.toString());

        WebUtils.redirect("/maintenance?page=" + currentPage, req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
