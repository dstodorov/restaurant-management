package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.dto.MenuItemDTO;
import com.dst.restaurantmanagement.models.dto.OrderDetailsDTO;
import com.dst.restaurantmanagement.models.dto.OrderedItemDTO;
import com.dst.restaurantmanagement.models.dto.UserOpenOrderDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.services.MenuItemService;
import com.dst.restaurantmanagement.services.OrderService;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
@Validated
public class WaiterController {

    private final RestaurantTableService restaurantTableService;
    private final OrderService orderService;
    private final MenuItemService menuItemService;


    @GetMapping
    public String waiterPage(Model model, @AuthenticationPrincipal RMUserDetails userDetails) {

        List<UserOpenOrderDTO> userOrders = this.orderService.getCurrentUserUsedTablesIds(userDetails);
        List<OrderedItemDTO> orderedItems = this.orderService.getOrderedItemsByUser(userDetails);

        model.addAttribute("userOrders", userOrders);
        model.addAttribute("orderedItems", orderedItems);

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

    @GetMapping("/{orderId}/add")
    public String addItemPage(@PathVariable Long orderId, Model model) {

        Map<String, List<MenuItemDTO>> availableMenuItems = this.menuItemService.getAvailableMenuItems();

        model.addAttribute("availableMenuItems", availableMenuItems);
        model.addAttribute("orderId", orderId);

        return "waiter-add-to-order";
    }

    @PostMapping("/{orderId}/add")
    public String addItem(@RequestParam Long itemId, @PathVariable Long orderId) {

        this.orderService.addToOrder(orderId, itemId);

        return "redirect:/service/{orderId}/add";
    }

    @GetMapping("/serve")
    public String serveOrderedItem(@RequestParam Long id) {

        this.orderService.serve(id);

        return "redirect:/service";
    }

    @GetMapping("/preview")
    public String previewOrder(@RequestParam Long orderId, Model model) {

        OrderDetailsDTO order = this.orderService.getOrderById(orderId);

        model.addAttribute("order", order);

        return "waiter-close-order";
    }

    @GetMapping("/close")
    public String closeOrder(@RequestParam Long orderId) {

        return "redirect:/service";
    }
}
