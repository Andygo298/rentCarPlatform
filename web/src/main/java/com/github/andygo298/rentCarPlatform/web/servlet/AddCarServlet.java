package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.Car;
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


@WebServlet(urlPatterns = "/addNewCar")
public class AddCarServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AddCarServlet.class);

    private CarService carService = DefaultCarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtils.forward("addCar", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String type = req.getParameter("type");
        String year_mfg = req.getParameter("year_mfg");
        double day_price = Double.parseDouble(req.getParameter("day_price"));
        Car newCar = new Car.CarBuilder(null)
                .withBrand(brand)
                .withModel(model)
                .withType(type)
                .withYear(year_mfg)
                .withPrice(day_price).build();
        carService.saveCar(newCar);
        String lastPage = String.valueOf(carService.getCountPages(carService.getCountRecordsFromCar()));
        log.info("addNewCar {} logged", newCar.toString());
        req.getSession().setAttribute("TEST", "TTT");
        WebUtils.redirect("/homepage?page=" + lastPage, req, resp);
    }
}
