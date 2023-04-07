package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {
    private final String EXISTING_USER = "admin";
    private final String EXISTING_USER_PASSWORD = "password";
    private final String EXISTING_USER_ROLE = "ROLE_ADMIN";
    private final String NOT_EXISTING_USER = "not_existing";

    private AppUserDetailsService appUserDetailsService;

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @BeforeEach
    void setUp() {
        this.appUserDetailsService = new AppUserDetailsService(
                mockEmployeeRepository
        );

    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void testEmployeeFound() {

        Employee testEmployee = new Employee();
        Role testRole = new Role();
        testRole.setRoleType(RoleType.ADMIN);
        testEmployee.setUsername(EXISTING_USER);
        testEmployee.setPassword(EXISTING_USER_PASSWORD);
        testEmployee.setRole(testRole);

        when(mockEmployeeRepository.findByUsername(EXISTING_USER)).thenReturn(Optional.of(testEmployee));

        UserDetails adminDetails = appUserDetailsService.loadUserByUsername(EXISTING_USER);

        Assertions.assertNotNull(adminDetails);

        Assertions.assertEquals(EXISTING_USER, adminDetails.getUsername());
        Assertions.assertEquals(EXISTING_USER_PASSWORD, adminDetails.getPassword());

        Assertions.assertEquals(1, adminDetails.getAuthorities().size());
        Assertions.assertTrue(adminDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().contains(EXISTING_USER_ROLE)));
    }

    @Test
    void testEmployeeNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> appUserDetailsService.loadUserByUsername(NOT_EXISTING_USER));
    }
}