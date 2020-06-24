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
        String brand = addCar.getBrand();
        String model = addCar.getModel();
        String type = addCar.getType();
        String year_mfg = addCar.getYear_mfg();
        double day_price = addCar.getDay_price();
        Car newCar = new Car.CarBuilder(null)
                .withBrand(brand)
                .withModel(model)
                .withType(type)
                .withYear(year_mfg)
                .withPrice(day_price).build();
        carService.saveCar(newCar);
        log.info("--- Was create new Car - {}", newCar.toString());
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

        long carId = editRq.getCarId();
        String brand = editRq.getBrand();
        String model = editRq.getModel();
        String type = editRq.getType();
        String year_mfg = editRq.getYear_mfg();
        String img_url = editRq.getImg_url();
        double day_price = editRq.getDay_price();
        EditCar editCar = new EditCar.CarBuilder(carId)
                .withBrand(brand)
                .withModel(model)
                .withType(type)
                .withYear(year_mfg)
                .withImg(img_url)
                .withPrice(day_price)
                .build();
        carService.editCar(editCar);
        log.info("Car id={} was edit.", carId);
        return "redirect:/home?reqPage=" + page;
    }

    @GetMapping("/deleteCar")
    public String deleteCar(@CookieValue(value = "currentPageCar") String page, @RequestParam Long carId) {
        carService.delCar(carId);
        log.info("Car id={} was delete.", carId);
        return "redirect:/home?reqPage=" + page;
    }
}
