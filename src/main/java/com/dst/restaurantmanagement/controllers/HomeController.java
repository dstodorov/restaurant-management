package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
    private final EmployeeService employeeService;

    @GetMapping
    public String home(Authentication authentication) {
        if (authentication != null) {
            Employee loggedUser = this.employeeService.getByUsername(authentication.getName()).get();
            switch (loggedUser.getRole().getRoleType()) {

                case COOK -> {
                    return "redirect:/cook";
                }
                case WAITER -> {

                }
                case MANAGER -> {
                    return "redirect:/manage";
                }
                case WAREHOUSE_WORKER -> {

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
}
