package com.github.andygo298.rentCarPlatform.web.controller;

import com.github.andygo298.rentCarPlatform.model.Car;
import com.github.andygo298.rentCarPlatform.model.Staff;
import com.github.andygo298.rentCarPlatform.model.actions.EditStaff;
import com.github.andygo298.rentCarPlatform.service.CarService;
import com.github.andygo298.rentCarPlatform.service.ServiceUtil;
import com.github.andygo298.rentCarPlatform.service.StaffService;
import com.github.andygo298.rentCarPlatform.web.WebUtils;
import com.github.andygo298.rentCarPlatform.web.rq.EditWorkerRq;
import com.github.andygo298.rentCarPlatform.web.rq.SaveWorkerRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class StaffController {
    private static final Logger log = LoggerFactory.getLogger(StaffController.class);
    private final CarService carService;
    private final StaffService staffService;

    public StaffController(CarService carService, StaffService staffService) {
        this.carService = carService;
        this.staffService = staffService;
    }

    @GetMapping()
    public String getCarAndStaff(@RequestParam(required = false) Integer reqPage, ModelMap modelMap, HttpServletResponse resp) {
        Integer page = reqPage != null
                ? reqPage
                : 1;

        double countRecordsFromCar = carService.getCountRecordsFromCar();
        int countPages = ServiceUtil.getCountPages(countRecordsFromCar);

        List<Car> cars = carService.getCars(page);
        List<Staff> staffAllList = staffService.getStaffWithoutPagination();

        modelMap.addAttribute("carsStaff", cars);
        modelMap.addAttribute("countPagesService", countPages);
        modelMap.addAttribute("currentPageService", page);
        modelMap.addAttribute("staffAll", staffAllList);

        Cookie currentPage = new Cookie("currentPageMaintenance", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);
        return "maintenance";
    }

    @GetMapping("/list")
    public String getListStaff(@RequestParam(required = false) Integer reqPage, ModelMap modelMap, HttpServletResponse resp) {
        Integer page = reqPage != null
                ? reqPage
                : 1;

        double countRecordsFromStaff = staffService.getCountRecordsFromStaff();
        int countPagesStaff = ServiceUtil.getCountPages(countRecordsFromStaff);
        List<Staff> staffList = staffService.getStaff(page);

        modelMap.addAttribute("staff", staffList);
        modelMap.addAttribute("countPagesStaff", countPagesStaff);
        modelMap.addAttribute("currentPageStaff", page);

        Cookie currentPage = new Cookie("currentPageStaff", Integer.toString(page));
        currentPage.setMaxAge(-1);
        resp.addCookie(currentPage);

        return "staffs";
    }

    @GetMapping("/list/newWorker")
    public String newWorker(@CookieValue(value = "currentPageStaff") String page, ModelMap modelMap) {
        modelMap.addAttribute("pageStaff", page);
        return "addWorker";
    }

    @PostMapping("/list/newWorker")
    public String add(SaveWorkerRq saveWorker) {
        String firstName = saveWorker.getFirstName();
        String lastName = saveWorker.getLastName();
        String specialization = saveWorker.getSpecialization();

        Staff newStaff = new Staff.StaffBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSpecialization(WebUtils.getSpecEnum(specialization))
                .withCar(new ArrayList<>())
                .build();
        String lastPage = String.valueOf(ServiceUtil.getCountPages(staffService.getCountRecordsFromStaff()));
        log.info("--- Created new worker: {} logged", newStaff.toString());
        Long aLong = staffService.saveStaff(newStaff);
        return "redirect:/maintenance/list?reqPage=" + lastPage;
    }

    @GetMapping("/list/editWorker")
    public String editWorker(@RequestParam Long id,
                             ModelMap modelMap,
                             @CookieValue(value = "currentPageStaff") String page) {
        Staff workerToEdit = staffService.getPersonById(id);
        modelMap.addAttribute("editWorker", workerToEdit);
        modelMap.addAttribute("staffPage", page);
        return "editWorker";
    }

    @PostMapping("/list/editWorker")
    public String edit(@CookieValue(value = "currentPageStaff") String page,
                       EditWorkerRq editWorker,
                       @RequestParam Long id) {

        String firstName = editWorker.getFirstName();
        String lastName = editWorker.getLastName();
        String specialization = editWorker.getSpecialization();

        EditStaff staffToEdit = new EditStaff.EditStaffBuilder(id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSpecialization(WebUtils.getSpecEnum(specialization))
                .build();

        staffService.editStaff(staffToEdit);
        log.info("--- Worker id={} was edited", id);
        return "redirect:/maintenance/list?reqPage=" + page;
    }

    @GetMapping("list/delWorker")
    public String delWorker(@CookieValue(value = "currentPageStaff") String page,
                            @RequestParam Long id) {
        staffService.delStaff(id);
        log.info("--- Deleted Worker id={}", id);
        return "redirect:/maintenance/list?reqPage=" + page;
    }

    @PostMapping("/list/addWorkersToCar")
    public String addWorkersToCar(@CookieValue(value = "currentPageMaintenance") String page,
                                  @RequestParam Long selectedCarId,
                                  @RequestParam(name = "specList") List<Long> specList) {
        Car carById = carService.getCarById(selectedCarId);
        List<Staff> staffListByIds = staffService.getStaffListByIds(specList);
        carService.saveStaffIntoCar(carById, staffListByIds);
        log.info("--- Workers id: {}, were add into Car id={}.", specList.toString(), selectedCarId);
        return "redirect:/maintenance?reqPage=" + page;
    }

    @GetMapping("/list/remove")
    public String removeWorkerFromCar(@CookieValue(value = "currentPageMaintenance") String page,
                                      @RequestParam Long remCarId,
                                      @RequestParam Long remSpecId){
        if (page == null) page ="1";
        staffService.removeStaffFromCar(remCarId, remSpecId);
        log.warn("--- Worker id= {} was deleted from car id= {}", remCarId.toString(), remSpecId.toString());
        return "redirect:/maintenance?reqPage=" + page;
    }
}
