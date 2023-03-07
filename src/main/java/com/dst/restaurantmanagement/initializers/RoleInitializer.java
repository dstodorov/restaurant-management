package com.dst.restaurantmanagement.initializers;

import com.dst.restaurantmanagement.services.EmployeeService;
import com.dst.restaurantmanagement.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleInitializer implements CommandLineRunner, AppInitializer {

    private final RoleService roleService;
    private final EmployeeService employeeService;

    @Override
    public void run(String... args) {
        initRoles();
        initAdmin();
    }

    @Override
    public void initRoles() {
        if (!roleService.isRolesInitialized()) {
            roleService.initRoles();
        }
    }

    @Override
    public void initAdmin() {
        if (!this.employeeService.isAdministratorInitialized()) {
            this.employeeService.initAdministrator();
        }
    }
}
