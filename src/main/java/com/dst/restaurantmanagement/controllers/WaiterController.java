package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.dto.AvailableMenuItemsDTO;
import com.dst.restaurantmanagement.models.dto.MenuItemDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.services.MenuItemService;
import com.dst.restaurantmanagement.services.OrderService;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class WaiterController {

    private final RestaurantTableService restaurantTableService;
    private final OrderService orderService;
    private final MenuItemService menuItemService;

    @GetMapping
    public String waiterPage(Model model, @AuthenticationPrincipal RMUserDetails userDetails) {

        List<Long> currentUserUsedTablesIds = this.orderService.getCurrentUserUsedTablesIds(userDetails);

        model.addAttribute("usedTables", currentUserUsedTablesIds);

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

    @GetMapping("/{tableId}/add")
    public String addConsumablePage(@PathVariable Long tableId, Model model) {

        Map<String, List<MenuItemDTO>> availableMenuItems = this.menuItemService.getAvailableMenuItems();

        model.addAttribute("availableMenuItems", availableMenuItems);

        return "waiter-add-to-order";
    }
}
