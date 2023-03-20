package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.services.OrderService;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class WaiterController {

    private final RestaurantTableService restaurantTableService;
    private final OrderService orderService;

    @GetMapping
    public String waiterPage(Model model, Principal principal) {

        return "waiter-dashboard";
    }

    @GetMapping("/pending")
    public String pendingTables(Model model) {

        List<RestaurantTable> pendingTables = this.restaurantTableService.getAllPendingTables();

        model.addAttribute("pendingTables", pendingTables);

        return "waiter-pending-tables";
    }

    @GetMapping("/{tableId}/take")
    public String startServiceTable(@PathVariable Long tableId, Principal user) {
        this.orderService.startService(tableId, user);

        return "redirect:/service/pending";
    }
}
