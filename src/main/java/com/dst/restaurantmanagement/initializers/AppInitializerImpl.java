package com.dst.restaurantmanagement.initializers;

import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.services.EmployeeService;
import com.dst.restaurantmanagement.services.RestaurantTableService;
import com.dst.restaurantmanagement.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AppInitializerImpl implements CommandLineRunner, AppInitializer {

    private final RoleService roleService;
    private final EmployeeService employeeService;
    private final RestaurantTableService restaurantTableService;

    @Override
    public void run(String... args) {
        initRoles();
        initAdmin();
        initUsers();
        initTables();
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

    @Override
    public void initUsers() {
        this.employeeService.initUsers();
    }

    @Override
    public void initTables() {
        this.restaurantTableService.initTables();
    }
}
