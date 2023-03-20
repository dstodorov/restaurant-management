package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType role);
}
