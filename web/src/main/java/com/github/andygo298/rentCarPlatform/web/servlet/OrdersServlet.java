package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.*;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultOrderService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/orders")
public class OrdersServlet extends HttpServlet {
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = req.getParameter("page") != null
                ? Integer.parseInt(req.getParameter("page"))
                : 1;

        Cookie currentPage = new Cookie("currentPageOrder", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);

        AuthUser authUser = (AuthUser) req.getSession().getAttribute("authUser");
        User user = (User) req.getSession().getAttribute("activeUser");

        List<Order> orders = authUser.getRole().equals(Role.ADMIN)
                ? orderService.getOrders(page)
                : orderService.getUserOrders(user.getId(), page);

        List<OrderInfo> orderInfoList = orderService.buildOrdersInfo(orders);

        int countRecordsFromOrder = authUser.getRole().equals(Role.ADMIN)
                ? orderService.getCountRecordsFromOrders()
                : orderService.getCountRecordsFromOrdersForUser(user.getId());

        int countPages = orderService.getCountPages(countRecordsFromOrder);



        req.setAttribute("orders", orderInfoList);
        req.setAttribute("countPages", countPages);
        req.setAttribute("currentPageOrder", page);
        WebUtils.forward("orders", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
