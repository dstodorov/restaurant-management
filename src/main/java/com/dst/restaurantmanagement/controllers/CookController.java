package com.dst.restaurantmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cook")
public class CookController {

    @GetMapping
    public String cookPage() {
        return "cook-page";
    }
}
