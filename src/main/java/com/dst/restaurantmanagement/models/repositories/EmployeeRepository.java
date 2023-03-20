package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> getByUsername(String username);

    Optional<Employee> getByPhoneNumber(String phoneNumber);

    Optional<Employee> findByUsername(String adminUsername);
}
