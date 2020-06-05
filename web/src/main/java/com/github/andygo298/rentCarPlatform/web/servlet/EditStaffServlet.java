package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultStaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/editStaff")
public class EditStaffServlet extends HttpServlet {
    private StaffService staffService = DefaultStaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long staffId = Long.valueOf(req.getParameter("id"));
        Staff workerToEdit = staffService.getPersonById(staffId);
        req.setAttribute("editWorker", workerToEdit);
        WebUtils.forward("editStaff", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        Integer currentPageStaff = (Integer) servletContext.getAttribute("currentPageStaff");

        Staff personById = staffService.getPersonById(Long.valueOf(req.getParameter("id")));

        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String specialization = req.getParameter("specialization");

        EditStaff staffToEdit = new EditStaff.EditStaffBuilder(personById.getId())
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSpecialization(WebUtils.getSpecEnum(specialization))
                .build();


        staffService.editStaff(staffToEdit);
        WebUtils.redirect("/staff?page=" + currentPageStaff, req, resp);
    }
}
