package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.ConsumableType;
import com.dst.restaurantmanagement.models.dto.AddConsumableDTO;
import com.dst.restaurantmanagement.models.entities.Consumable;
import com.dst.restaurantmanagement.services.ConsumableService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

    private final ConsumableService consumableService;

    @ModelAttribute(name = "addConsumableDTO")
    public AddConsumableDTO initAddConsumableDTO() {
        return new AddConsumableDTO();
    }

    @GetMapping
    public String inventoryPage(Model model) {

        List<Consumable> consumables = this.consumableService.getExpiringConsumables();
        List<Consumable> appetizers = this.consumableService.getConsumablesByType(ConsumableType.APPETIZER);
        List<Consumable> drinks = this.consumableService.getConsumablesByType(ConsumableType.DRINK);
        List<Consumable> dishes = this.consumableService.getConsumablesByType(ConsumableType.DISH);
        List<Consumable> salads = this.consumableService.getConsumablesByType(ConsumableType.SALAD);
        List<Consumable> desserts = this.consumableService.getConsumablesByType(ConsumableType.DESSERT);

        model.addAttribute("consumables", consumables);
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("appetizers", appetizers);
        model.addAttribute("drinks", drinks);
        model.addAttribute("dishes", dishes);
        model.addAttribute("salads", salads);
        model.addAttribute("desserts", desserts);

        return "worker-inventory";
    }

    @GetMapping("/add")
    public String addConsumablePage(Model model) {

        List<String> consumableTypes = Arrays.stream(ConsumableType.values()).map(ConsumableType::name).toList();

        model.addAttribute("consumableTypes", consumableTypes);

        return "worker-inventory-add";
    }

    @PostMapping("/add")
    public String addConsumable(@Valid AddConsumableDTO addConsumableDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addConsumableDTO", addConsumableDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addConsumableDTO", bindingResult);

            return "redirect:/inventory/add";
        }

        this.consumableService.saveConsumable(addConsumableDTO);

        return "redirect:/inventory";
    }
}
