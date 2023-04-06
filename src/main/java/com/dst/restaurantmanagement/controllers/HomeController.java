package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.LogEntry;
import com.dst.restaurantmanagement.services.EmployeeService;
import com.dst.restaurantmanagement.services.LogEntryService;
import com.dst.restaurantmanagement.services.StatusLogService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
    private final EmployeeService employeeService;
    private final LogEntryService logEntryService;

    @GetMapping
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Employee loggedUser = this.employeeService.getByUsername(authentication.getName()).get();

            switch (loggedUser.getRole().getRoleType()) {

                case COOK -> {
                    return "redirect:/cook";
                }
                case WAITER -> {
                    return "redirect:/service";
                }
                case MANAGER -> {
                    return "redirect:/manage";
                }
                case WAREHOUSE_WORKER -> {
                    return "redirect:/inventory";
                }
                case ADMIN -> {
                    return "redirect:/employees";
                }
                case HOST -> {
                    return "redirect:/host";
                }
            }
        }

        return "login";
    }

    @GetMapping("/events")
    public String getStatuses(Model model) {
        model.addAttribute("events_menu", true);
        return "admin-events";
    }

    @GetMapping("/access")
    public String getAccessLogs(Model model) {
        model.addAttribute("access_menu", true);

        List<LogEntry> allLogs = this.logEntryService.getAllLogs();

        model.addAttribute("logs", allLogs);

        return "admin-access";
    }
}
