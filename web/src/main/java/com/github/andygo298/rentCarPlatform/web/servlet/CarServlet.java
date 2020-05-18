package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.EditCar;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
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
import java.util.Arrays;
import java.util.Optional;

@WebServlet(urlPatterns = "/editCar")
public class CarServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServlet.class);
    private CarService carService = DefaultCarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long carId = Long.parseLong(req.getParameter("id"));
        Car carById = carService.getCarById(carId);
        req.setAttribute("carInstance", carById);
        WebUtils.forward("editCar", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = WebUtils.readCookie(req, "currentPageCar");

        long id = Long.parseLong(req.getParameter("id"));
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String type = req.getParameter("type");
        String year = req.getParameter("year_mfg");
        String img_url = req.getParameter("img_url");
        double day_price = Double.parseDouble(req.getParameter("day_price"));
        EditCar editCar = new EditCar.CarBuilder(id)
                .withBrand(brand)
                .withModel(model)
                .withType(type)
                .withYear(year)
                .withImg(img_url)
                .withPrice(day_price)
                .build();
        carService.editCar(editCar);
        log.info("editCar {} logged", editCar.toString());
        WebUtils.redirect("/homepage?page=" + currentPage, req, resp);
    }
}
