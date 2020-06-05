package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;
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
import java.util.ArrayList;
import java.util.HashSet;

@WebServlet(urlPatterns = "/createStaff")
public class CreateStaffServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CreateStaffServlet.class);
    private StaffService staffService = DefaultStaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtils.forward("createStaff", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String specialization = req.getParameter("specialization");

        Staff newStaff = new Staff.StaffBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSpecialization(WebUtils.getSpecEnum(specialization))
                .withCar(new ArrayList<>())
                .build();
        String lastPage = String.valueOf(ServiceUtil.getCountPages(staffService.getCountRecordsFromStaff()));
        log.info("Create new worker {} logged", newStaff.toString());
        Long aLong = staffService.saveStaff(newStaff);
        WebUtils.redirect("/staff?page=" + lastPage, req, resp);

    }
}
