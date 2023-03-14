package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/host")
public class HostController {

    private final RestaurantTableService restaurantTableService;

    @GetMapping
    public String hostPage() {
        return "redirect:/host/accommodation";
    }

    @GetMapping("/accommodation")
    public String accommodationPage(Model model) {

        List<RestaurantTable> tables = this.restaurantTableService.getTables();

        model.addAttribute("tables", tables);

        return "host-accommodation";
    }

    @GetMapping("/accommodation/table")
    public String accommodationPageWithTables(Model model, @RequestParam Integer seats) {

        List<RestaurantTable> tables = this.restaurantTableService.getTables(seats);

        model.addAttribute("tables", tables);

        return "host-accommodation";
    }

    @GetMapping("/accommodate")
    public String accommodate(@RequestParam Long id) {
        System.out.println(id);

        return "redirect:/host/accommodation";
    }
}
