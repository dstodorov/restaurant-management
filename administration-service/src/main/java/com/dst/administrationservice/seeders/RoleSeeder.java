package com.dst.administrationservice.seeders;

import com.dst.administrationservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleService roleService;

    @Autowired
    public RoleSeeder(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        if (!this.roleService.isRolesLoaded()) {
            this.roleService.seedRoles();
        }
    }
}
