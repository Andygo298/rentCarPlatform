package com.github.andygo298.rentCarPlatform.web.servlet;

import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.model.User;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.PaymentService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultOrderService;
import com.github.andygo298.rentCarPlatform.service.impl.DefaultPaymentService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/payment")
public class PaymentServlet extends HttpServlet {
    private OrderService orderService = DefaultOrderService.getInstance();
    private PaymentService paymentService = DefaultPaymentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        Double orderPrice = orderService.getOrderPriceById(orderId);
        req.setAttribute("orderId", orderId);
        req.setAttribute("orderPrice", orderPrice);
        WebUtils.forward("payment", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        Object currentUser = req.getSession().getAttribute("activeUser");
        Double orderPrice = Double.parseDouble(req.getParameter("orderPrice"));
        String cardNum = req.getParameter("cardNum");
        Payment newPayment = new Payment.PaymentBuilder()
                .withCardNum(cardNum)
                .withPaymentValue(orderPrice)
                .withUser((User)currentUser)
                .build();
        paymentService.savePayment(newPayment);
        orderService.setOrderStatus(orderId, OrderStatus.COMPLETED);
        WebUtils.redirect("/orders", req, resp);
    }
}
