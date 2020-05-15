package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.dao.ConverterDate;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultOrderService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/makeOrder")
public class MakeOrderServlet extends HttpServlet {
    private OrderService orderService = DefaultOrderService.getInstance();
    private CarService carService = DefaultCarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        WebUtils.forward("makeOrder", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String passport = req.getParameter("passport");
        String phone = req.getParameter("phone");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        Long carId = Long.valueOf(req.getParameter("id"));

        Car carById = carService.getCarById(carId);
        User currentUser = (User)(req.getSession().getAttribute("activeUser"));

        Long userId = currentUser.getId();
        Double price = orderService.calculateOrderPrice(startDate,endDate,carId);
        if (price < 0) {
            req.setAttribute("errorMessage", "Set valid dates.");
            req.setAttribute("id", req.getParameter("id"));
            WebUtils.forward("/makeOrder", req, resp);
            return;
        }
        Order order = new Order.OrderBuilder(carId, userId)
                .withPassport(passport)
                .withDates(ConverterDate.stringToDate(startDate),ConverterDate.stringToDate(endDate))
                .withTelephone(phone)
                .withPrice(price)
                .withCar(carById)
                .withUser(currentUser)
                .build();
        Long orderId = orderService.saveOrder(order);
        if (orderId != null) {
            carService.changeRentStatus(carId,true);
            WebUtils.redirect("/homepage",req,resp);
        } else {
            req.setAttribute("errorMessage", "Something went wrong, contact admin or try again later.");
        }
    }
}
