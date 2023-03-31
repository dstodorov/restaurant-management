package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.dto.AddTableDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class ManagerController {

    private final RestaurantTableService restaurantTableService;

    @ModelAttribute(name = "addTableDTO")
    public AddTableDTO initAddTableDTO() {
        return new AddTableDTO();
    }

    @GetMapping
    public String managerPage() {
        return "redirect:/manage/tables";
    }

    @GetMapping("/tables")
    public String getTables(Model model) {
        List<RestaurantTable> allTables = this.restaurantTableService.getTables();

        model.addAttribute("allTables", allTables);

        return "manager-tables";
    }

    @GetMapping("/tables/add")
    public String addTablePage() {
        return "manager-add-table";
    }

    @PostMapping("/tables/add")
    public String addTable(@Valid AddTableDTO addTableDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal RMUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addTableDTO", addTableDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTableDTO", bindingResult);

            return "redirect:/manage/tables/add";
        }

        this.restaurantTableService.saveTable(addTableDTO, userDetails);

        return "redirect:/manage/tables";
    }

    @GetMapping("/tables/delete/")
    public String deleteTable(@RequestParam Long id) {
        this.restaurantTableService.deleteTable(id);
        return "redirect:/manage/tables";
    }
}
