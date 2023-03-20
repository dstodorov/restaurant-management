package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.models.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void initRoles() {
        List<Role> roles = Arrays.stream(RoleType.values()).map(role -> new Role(RoleType.valueOf(role.name()))).toList();

        this.roleRepository.saveAll(roles);
    }

    public Boolean isRolesInitialized() {
        return this.roleRepository.count() > 0;
    }

    public List<Role> getEmployeeRoles() {
        return this.roleRepository.findAll();
    }

}
