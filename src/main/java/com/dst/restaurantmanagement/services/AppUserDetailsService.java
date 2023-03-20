package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.models.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s was not found!", username)));
    }

    private UserDetails map(Employee employee) {
        return new RMUserDetails(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                getRoleAsGrantedAuthority(employee.getRole())
        );
    }

    private List<GrantedAuthority> getRoleAsGrantedAuthority(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleType().name()));

        return authorities;
    }
}
