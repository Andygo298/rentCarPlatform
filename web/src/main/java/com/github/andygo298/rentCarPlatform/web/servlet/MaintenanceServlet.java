package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@WebServlet(urlPatterns = "/maintenance")
public class MaintenanceServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);
    private CarService carService = DefaultCarService.getInstance();
    private StaffService staffService = DefaultStaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = req.getParameter("page") != null
                ? Integer.parseInt(req.getParameter("page"))
                : 1;

        double countRecordsFromCar = carService.getCountRecordsFromCar();
        int countPages = ServiceUtil.getCountPages(countRecordsFromCar);

        List<Car> cars = carService.getCars(page);
        List<Staff> staffAllList = staffService.getStaffWithoutPagination();

        req.setAttribute("cars", cars);
        req.setAttribute("countPagesService", countPages);
        req.setAttribute("currentPageService", page);
        req.setAttribute("staffAll", staffAllList);

        Cookie currentPage = new Cookie("currentPageMaintenance", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);

        WebUtils.forward("maintenance", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
