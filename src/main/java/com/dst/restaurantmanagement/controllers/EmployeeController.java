package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.dto.AddEmployeeDTO;
import com.dst.restaurantmanagement.models.dto.EditEmployeeDTO;
import com.dst.restaurantmanagement.models.dto.EmployeeInfoDTO;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.services.EmployeeService;
import com.dst.restaurantmanagement.services.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @ModelAttribute(name = "editEmployeeDTO")
    public EditEmployeeDTO initEditEmployeeDTO() {
        return new EditEmployeeDTO();
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

            return "redirect:/employees/add";
        }

        this.employeeService.saveEmployee(addEmployeeDTO);

        return "redirect:/employees";
    }

    @GetMapping
    public String employeeList(Model model) {
        List<EmployeeInfoDTO> employees = this.employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        return "admin-employees";
    }

    @RequestMapping(value = "/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        System.out.println(id);
        this.employeeService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployeePage(@PathVariable("id") Long id, Model model) {

        EditEmployeeDTO employee = this.employeeService.getEmployee(id);

        model.addAttribute("employee", employee);
        model.addAttribute("employeeId", id);

        List<Role> employeeRoles = this.roleService.getEmployeeRoles();

        model.addAttribute("employeeRoles", employeeRoles);

        return "admin-edit-employee";
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id, @Valid EditEmployeeDTO editEmployeeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editEmployeeDTO", editEmployeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editEmployeeDTO", bindingResult);

            return "redirect:/employees/edit/{id}";
        }
        return "redirect:/employees";
    }

}
