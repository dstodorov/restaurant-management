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
public class UserController {

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
        model.addAttribute("add_employee_menu", true);


        return "admin-add-new-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid AddEmployeeDTO addEmployeeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);

            return "redirect:/employees/add";
        }

        String errorMessage = this.employeeService.saveEmployee(addEmployeeDTO);

        if (this.employeeService.saveEmployee(addEmployeeDTO) != null) {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            redirectAttributes.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);

            return "redirect:/employees/add";
        }

        return "redirect:/employees";
    }

    @GetMapping
    public String employeeList(Model model) {
        List<EmployeeInfoDTO> employees = this.employeeService.getAllEmployees();

        model.addAttribute("employees", employees);
        model.addAttribute("employees_menu", true);

        return "admin-employees";
    }

    @RequestMapping(value = "/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        this.employeeService.disableAccount(id);

        return "redirect:/employees";
    }

    @GetMapping("/{id}/enable")
    public String enableAccount(@PathVariable Long id) {
        this.employeeService.enableAccount(id);

        return "redirect:/employees";
    }

    @GetMapping("/{id}/disable")
    public String disableAccount(@PathVariable Long id) {
        this.employeeService.disableAccount(id);

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

        this.employeeService.editEmployee(id, editEmployeeDTO);

        return "redirect:/employees";
    }

}
