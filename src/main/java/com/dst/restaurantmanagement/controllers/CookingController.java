package com.dst.restaurantmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cook")
public class CookingController {
    @GetMapping
    public String getCookingPage() {
        return "cook-page";
    }

    @GetMapping("/start")
    public String startCooking(@RequestParam Long id){

        return "redirect:/cook";
    }

    @GetMapping("/finish")
    public String finishCooking(@RequestParam Long id){

        return "redirect:/cook";
    }
}
