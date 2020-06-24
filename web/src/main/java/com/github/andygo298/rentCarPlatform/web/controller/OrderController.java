package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.dao.utils.ConverterDate;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Order;
import com.github.andygo298.rentCarPlatform.model.actions.OrderInfo;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.web.rq.MakeOrderRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final CarService carService;

    public OrderController(OrderService orderService, CarService carService) {
        this.orderService = orderService;
        this.carService = carService;
    }

    @GetMapping()
    public String orders(HttpServletResponse resp, @RequestParam(required = false) Integer reqPage, ModelMap modelMap) {
        Integer page = reqPage != null
                ? reqPage
                : 1;

        Cookie currentPage = new Cookie("currentPageOrder", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Order> orders = authUser.getRole().equals(Role.ADMIN)
                ? orderService.getOrders(page)
                : orderService.getUserOrders(authUser.getUserId(), page);

        List<OrderInfo> orderInfoList = orderService.buildOrdersInfo(orders);

        double countRecordsFromOrder = authUser.getRole().equals(Role.ADMIN)
                ? orderService.getCountRecordsFromOrders()
                : orderService.getCountRecordsFromOrdersForUser(authUser.getUserId());

        int countPages = ServiceUtil.getCountPages(countRecordsFromOrder);

        modelMap.addAttribute("orders", orderInfoList);
        modelMap.addAttribute("countPages", countPages);
        modelMap.addAttribute("currentPageOrder", page);
        return "orders";
    }

    @GetMapping("/make")
    public String add(@RequestParam long carId, ModelMap modelMap) {
        modelMap.addAttribute("carId", carId);

        return "makeOrder";
    }

    @PostMapping("/make")
    public String addOrder(@RequestParam long carId, MakeOrderRq makeOrder, ModelMap modelMap) {
        String passport = makeOrder.getPassport();
        String phone = makeOrder.getPhone();
        String startDate = makeOrder.getStartDate();
        String endDate = makeOrder.getEndDate();

        long userId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();

        Double price = orderService.calculateOrderPrice(startDate, endDate, carId);
        if (price < 0) {
            modelMap.addAttribute("errorMessage1", "Set valid dates.");
            modelMap.addAttribute("id", carId);
            return "makeOrder";
        }
        Order order = new Order.OrderBuilder(carId, userId)
                .withPassport(passport)
                .withDates(ConverterDate.stringToDate(startDate), ConverterDate.stringToDate(endDate))
                .withTelephone(phone)
                .withPrice(price)
                .build();
        Long orderId = orderService.saveOrder(order);
        if (orderId != null) {
            carService.changeRentStatus(carId, true);
            return "redirect:/home";
        } else {
            modelMap.addAttribute("errorMessage2", "Something went wrong, contact admin or try again later.");
        }
        return "redirect:/home";
    }

    @GetMapping("/approveOrder")
    public String approve(@RequestParam Long orderId) {
        orderService.setOrderStatus(orderId, OrderStatus.ACCEPTED);
        return "redirect:/orders";
    }

    @GetMapping("rejectOrder")
    public String reject(@RequestParam Long orderId) {
        orderService.setOrderStatus(orderId, OrderStatus.REJECTED);
        Long carId = orderService.getCarIdByOrder(orderId);
        carService.changeRentStatus(carId, false);
        return "redirect:/orders";
    }
}
