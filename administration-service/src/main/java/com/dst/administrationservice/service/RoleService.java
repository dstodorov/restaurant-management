package com.dst.administrationservice.service;

import com.dst.administrationservice.models.Role;
import com.dst.administrationservice.models.UserRoles;
import com.dst.administrationservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void seedRoles() {
        List<Role> roles = new ArrayList<>();
        Arrays.stream(UserRoles.values())
                .forEach(role -> {
                    Role r = new Role();
                    r.setRole(role);
                    roles.add(r);
                });

        this.roleRepository.saveAll(roles);
    }

    public Boolean isRolesLoaded() {
        return this.roleRepository.count() > 0;
    }

    public Optional<Role> findByUserRole(UserRoles role) {
        return roleRepository.findByRole(role);
    }
}
