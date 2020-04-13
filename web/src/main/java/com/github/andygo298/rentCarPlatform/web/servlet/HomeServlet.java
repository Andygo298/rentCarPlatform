package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.Role;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultCarService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultOrderService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/homepage")
public class HomeServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);
    private CarService carService = DefaultCarService.getInstance();
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Car> cars = carService.getCars();
        req.setAttribute("cars", cars);
        AuthUser user = (AuthUser) req.getSession().getAttribute("authUser");
        Integer activeOrders = user.getRole().equals(Role.ADMIN)
                ? orderService.getOrdersByStatus(OrderStatus.IN_PROGRESS)
                : orderService.getUserOrdersByStatus(OrderStatus.ACCEPTED, user.getUserId());

        req.setAttribute("activeOrders", activeOrders);
        WebUtils.forward("homepage", req, resp);

    }
}
