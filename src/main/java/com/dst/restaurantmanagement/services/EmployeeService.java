package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.exceptions.PhoneNumberDuplicationException;
import com.dst.restaurantmanagement.exceptions.UsernameDuplicationException;
import com.dst.restaurantmanagement.models.dto.AddEmployeeDTO;
import com.dst.restaurantmanagement.models.dto.EditEmployeeDTO;
import com.dst.restaurantmanagement.models.dto.EmployeeInfoDTO;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import com.dst.restaurantmanagement.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Value("${app.admin.username}")
    private String adminUsername;
    @Value("${app.admin.password}")
    private String adminPassword;
    @Value("${app.admin.firstName}")
    private String adminFirstName;
    @Value("${app.admin.lastName}")
    private String adminLastName;
    @Value("${app.admin.phoneNumber}")
    private String adminPhoneNumber;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveEmployee(AddEmployeeDTO employeeDTO) {

        Optional<Employee> employeeByUsername = this.employeeRepository.getByUsername(employeeDTO.getUsername());
        Optional<Employee> employeeByPhoneNumber = this.employeeRepository.getByPhoneNumber(employeeDTO.getPhoneNumber());

        if (employeeByUsername.isPresent()) {
            throw new UsernameDuplicationException(String.format("User with username %s, already exists!", employeeDTO.getUsername()));
        }

        if (employeeByPhoneNumber.isPresent()) {
            throw new PhoneNumberDuplicationException(String.format("User with phone number %s, already exists!", employeeDTO.getPhoneNumber()));
        }

        Role employeeRole = roleRepository.findByRoleType(RoleType.valueOf(employeeDTO.getRole()));

        Employee employee = mapper.map(employeeDTO, Employee.class);

        employee.setRole(employeeRole);

        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

        this.employeeRepository.save(employee);
    }

    public void initAdministrator() {
        Role role = this.roleRepository.findByRoleType(RoleType.ADMIN);

        Employee employee = new Employee(
                adminFirstName,
                adminLastName,
                adminUsername,
                passwordEncoder.encode(adminPassword),
                adminPhoneNumber,
                LocalDate.now(),
                role
        );

        this.employeeRepository.save(employee);
    }

    public Boolean isAdministratorInitialized() {
        Optional<Employee> admin = this.employeeRepository.findByUsername(this.adminUsername);

        return admin.isPresent() && passwordEncoder.matches(adminPassword, admin.get().getPassword());
    }

    public List<EmployeeInfoDTO> getAllEmployees() {

        return this.employeeRepository.findAll().stream().filter(e -> !e.getRole().getRoleType().equals(RoleType.ADMIN)).map(this::mapToEmployeeInfoDTO).toList();
    }

    private EmployeeInfoDTO mapToEmployeeInfoDTO(Employee employee) {
        return new EmployeeInfoDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getRole().getRoleType().name()
        );
    }

    public void delete(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
    }

    public EditEmployeeDTO getEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        return mapper.map(employee.get(), EditEmployeeDTO.class);
    }

    public Optional<Employee> getByUsername(String username) {
        return this.employeeRepository.getByUsername(username);
    }
}
