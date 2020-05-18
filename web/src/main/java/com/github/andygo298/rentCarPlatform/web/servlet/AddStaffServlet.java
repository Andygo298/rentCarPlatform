package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/addSpecialist")
public class AddStaffServlet extends HttpServlet {
    private StaffService staffService = DefaultStaffService.getInstance();
    private CarService carService = DefaultCarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = WebUtils.readCookie(req, "currentPageMaintenance");

        Car carById = carService.getCarById(Long.parseLong(req.getParameter("selectedCarId")));
        List<Long> staffListIds = Arrays.stream(req.getParameterValues("specList"))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Staff> staffListByIds = staffService.getStaffListByIds(staffListIds);

        carService.saveStaffIntoCar(carById, staffListByIds);
        WebUtils.redirect("/maintenance?page="+ currentPage, req, resp);
    }
}
