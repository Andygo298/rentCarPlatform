package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.actions.EditCar;
import com.github.andygo298.rentCarPlatform.model.enums.OrderStatus;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.OrderService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.web.rq.AddCarCreateRq;
import com.github.andygo298.rentCarPlatform.web.rq.EditCarRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
//TODO исправить в форме авто названия переменных
@Controller
@RequestMapping("/home")
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final OrderService orderService;

    public CarController(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String homePage(HttpServletResponse resp, @RequestParam(required = false) Integer reqPage, ModelMap modelMap) {
        Integer page = reqPage != null
                ? reqPage
                : 1;

        double countRecordsFromCar = carService.getCountRecordsFromCar();
        int countPages = ServiceUtil.getCountPages(countRecordsFromCar);

        Cookie currentPage = new Cookie("currentPageCar", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);

        List<Car> cars = carService.getCars(page);

        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("countPages", countPages);
        modelMap.addAttribute("currentPageCar", page);

        AuthUser userP = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer activeOrders = userP.getRole().equals(Role.ADMIN)
                ? orderService.getOrdersByStatus(OrderStatus.IN_PROGRESS)
                : orderService.getUserOrdersByStatus(OrderStatus.ACCEPTED, userP.getUserId());

        modelMap.addAttribute("activeOrders", activeOrders);
        return "home";
    }

    @GetMapping("/addCar")
    public String addCar(@CookieValue(value = "currentPageCar") String page, ModelMap modelMap) {
        modelMap.addAttribute("pageAdd", page);
        return "addCar";
    }

    @PostMapping("/addCar")
    public String add(AddCarCreateRq addCar) {
        Car newCar = new Car.CarBuilder(null)
                .withBrand(addCar.getBrand())
                .withModel(addCar.getModel())
                .withType(addCar.getType())
                .withYear(addCar.getYear_mfg())
                .withPrice(addCar.getDayPrice())
                .build();
        carService.saveCar(newCar);
        log.info("--- Was create new Car - {}", newCar);
        return "redirect:/home?reqPage=1";
    }

    @GetMapping("/editCar")
    public String edit(@RequestParam long carId, ModelMap modelMap, @CookieValue(value = "currentPageCar") String page) {
        Car carById = carService.getCarById(carId);
        modelMap.addAttribute("carInstance", carById);
        modelMap.addAttribute("pageEdit", page);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String edit(@CookieValue(value = "currentPageCar") String page, EditCarRq editRq) {

        EditCar editCar = new EditCar.CarBuilder(editRq.getCarId())
                .withBrand(editRq.getBrand())
                .withModel(editRq.getModel())
                .withType(editRq.getType())
                .withYear(editRq.getYear_mfg())
                .withImg(editRq.getImg_url())
                .withPrice(editRq.getDay_price())
                .build();
        carService.editCar(editCar);
        log.info("Car id={} was edit.", editRq.getCarId());
        return "redirect:/home?reqPage=" + page;
    }

    @GetMapping("/deleteCar")
    public String deleteCar(@CookieValue(value = "currentPageCar") String page, @RequestParam Long carId) {
        carService.delCar(carId);
        log.info("Car id={} was delete.", carId);
        return "redirect:/home?reqPage=" + page;
    }
}
