package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.models.dto.CookingItemDTO;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.services.OrderService;
import com.dst.restaurantmanagement.services.OrderedMenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cook")
@AllArgsConstructor
public class CookingController {

    private final OrderService orderService;
    private final OrderedMenuItemService orderedMenuItemService;

    @GetMapping
    public String getCookingPage(Model model) {

        List<CookingItemDTO> orderedItems = this.orderService.getOrderedItemsByStatus(DishStatus.ORDERED);
        List<CookingItemDTO> cookingItems = this.orderService.getOrderedItemsByStatus(DishStatus.COOKING);

        model.addAttribute("orderedItems", orderedItems);
        model.addAttribute("cookingItems", cookingItems);

        return "cook-page";
    }

    @GetMapping("/start")
    public String startCooking(@RequestParam Long id, @AuthenticationPrincipal RMUserDetails userDetails) {

        orderedMenuItemService.startCook(id, userDetails);

        return "redirect:/cook";
    }

    @GetMapping("/finish")
    public String finishCooking(@RequestParam Long id, @AuthenticationPrincipal RMUserDetails userDetails) {

        orderedMenuItemService.finishCook(id, userDetails);

        return "redirect:/cook";
    }
}
