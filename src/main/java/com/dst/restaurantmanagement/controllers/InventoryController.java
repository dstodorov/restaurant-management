package com.dst.restaurantmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @GetMapping
    public String inventoryPage() {

        return "worker-inventory";
    }
}
