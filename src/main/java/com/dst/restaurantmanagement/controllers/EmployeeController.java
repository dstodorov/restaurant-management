package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.dto.AddEmployeeDTO;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.services.EmployeeService;
import com.dst.restaurantmanagement.services.RoleService;
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

import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final RoleService roleService;
    private final EmployeeService employeeService;

    @ModelAttribute(name = "addEmployeeDTO")
    public AddEmployeeDTO initAddEmployeeDTO() {
        return new AddEmployeeDTO();
    }

    @GetMapping("/add")
    public String addEmployeePage(Model model) {


        List<Role> employeeRoles = this.roleService.getEmployeeRoles();

        model.addAttribute("employeeRoles", employeeRoles);


        return "admin-add-new-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid AddEmployeeDTO addEmployeeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);

            return "redirect:/employee/add";
        }

        this.employeeService.saveEmployee(addEmployeeDTO);

        return "redirect:/employee/add";
    }

    @GetMapping
    public String employeeDashboard() {
        return "admin-page";
    }

}
