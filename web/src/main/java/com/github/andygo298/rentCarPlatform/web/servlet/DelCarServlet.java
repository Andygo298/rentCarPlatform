package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/deleteCar")
public class DelCarServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DelCarServlet.class);
    private CarService carService = DefaultCarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long delCarId = Long.parseLong(req.getParameter("id"));
        carService.delCar(delCarId);
        log.info("delCar with id=  {} logged", delCarId.toString());
        WebUtils.redirect("/homepage", req, resp);
    }
}
