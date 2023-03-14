package com.dst.restaurantmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class WaiterController {

    @GetMapping
    public String waiterPage() {
        return "redirect:/service/dashboard";
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "waiter-dashboard";
    }
}
