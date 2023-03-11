package com.dst.restaurantmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class ManagerController {

    @GetMapping
    public String managerPage() {
        return "redirect:/manage/tables";
    }

    @GetMapping("/tables")
    public String getTables() {

        return "manager-tables";
    }

    @PostMapping("/tables/add")
    public String addTable() {

        return "redirect:/manage/tables";
    }
}
