package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.models.dto.AddMenuItemDTO;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.services.MenuItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

    private final MenuItemService menuItemService;

    @ModelAttribute(name = "addMenuItemDTO")
    public AddMenuItemDTO initAddMenuItemDTO() {
        return new AddMenuItemDTO();
    }

    @GetMapping
    public String inventoryPage(Model model) {


        List<MenuItem> appetizers = this.menuItemService.getMenuItemsByType(ItemType.APPETIZER);
        List<MenuItem> drinks = this.menuItemService.getMenuItemsByType(ItemType.DRINK);
        List<MenuItem> dishes = this.menuItemService.getMenuItemsByType(ItemType.DISH);
        List<MenuItem> salads = this.menuItemService.getMenuItemsByType(ItemType.SALAD);
        List<MenuItem> desserts = this.menuItemService.getMenuItemsByType(ItemType.DESSERT);


        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("appetizers", appetizers);
        model.addAttribute("drinks", drinks);
        model.addAttribute("dishes", dishes);
        model.addAttribute("salads", salads);
        model.addAttribute("desserts", desserts);

        return "worker-inventory";
    }

    @GetMapping("/expiring")
    public String expiringMenuItems(Model model) {
        List<MenuItem> menuItems = this.menuItemService.getExpiringMenuItems();
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("localDate", LocalDate.now());
        
        return "worker-inventory-expiring";
    }

    @GetMapping("/add")
    public String addMenuItemPage(Model model) {

        List<String> menuItemTypes = Arrays.stream(ItemType.values()).map(ItemType::name).toList();

        model.addAttribute("menuItemTypes", menuItemTypes);

        return "worker-inventory-add";
    }

    @PostMapping("/add")
    public String addMenuItem(@Valid AddMenuItemDTO addMenuItemDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMenuItemDTO", addMenuItemDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMenuItemDTO", bindingResult);

            return "redirect:/inventory/add";
        }

        this.menuItemService.saveMenuItem(addMenuItemDTO);

        return "redirect:/inventory";
    }

    @GetMapping("{id}/waste")
    public String wasteMenuItem(@PathVariable Long id) {

        this.menuItemService.wasteMenuItem(id);

        return "redirect:/inventory/expiring";
    }
}
