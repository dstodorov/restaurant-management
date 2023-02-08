package com.dst.administrationservice.repository;

import com.dst.administrationservice.models.Role;
import com.dst.administrationservice.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(UserRoles role);
}
