package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.PaymentService;
import com.github.andygo298.rentCarPlatform.web.rq.SavePaymentRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping()
    public String getPayments(@RequestParam Long orderId, ModelMap modelMap) {
        Double orderPrice = orderService.getOrderPriceById(orderId);
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("orderPrice", orderPrice);
        return "payment";
    }

    @PostMapping()
    public String savePayment(SavePaymentRq savePayment) {
        Long userId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        Payment newPayment = new Payment.PaymentBuilder()
                .withCardNum(savePayment.getCardNum())
                .withPaymentValue(savePayment.getOrderPrice())
                .withUserId(userId)
                .build();
        paymentService.savePayment(newPayment);
        orderService.setOrderStatus(savePayment.getOrderId(), OrderStatus.COMPLETED);
        return "redirect:/orders";
    }
}
