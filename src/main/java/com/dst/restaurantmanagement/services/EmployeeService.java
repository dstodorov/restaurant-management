package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.exceptions.PhoneNumberDuplicationException;
import com.dst.restaurantmanagement.exceptions.UsernameDuplicationException;
import com.dst.restaurantmanagement.initializers.InitUsersData;
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

    @Value("#{new Boolean('${app.init.users}')}")
    private Boolean initUsers;
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

        // Throw duplication exception in case of unique constraint error
        validateUsernameAndPhoneNumber(employeeDTO.getUsername(), employeeDTO.getPhoneNumber());

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
                false,
                role
        );

        this.employeeRepository.save(employee);
    }

    public Boolean isAdministratorInitialized() {
        Optional<Employee> admin = this.employeeRepository.findByUsername(this.adminUsername);

        return admin.isPresent() && passwordEncoder.matches(adminPassword, admin.get().getPassword());
    }

    public List<EmployeeInfoDTO> getAllEmployees() {

        return this.employeeRepository
                .findAll()
                .stream()
                .filter(e -> !e.getRole().getRoleType().equals(RoleType.ADMIN))
                .map(this::mapToEmployeeInfoDTO).toList();
    }

    private EmployeeInfoDTO mapToEmployeeInfoDTO(Employee employee) {
        return new EmployeeInfoDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getEnabled(),
                employee.getRole().getRoleType().name()
        );
    }

    public void disableAccount(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        employee.ifPresent(e -> {
            e.setEnabled(false);
            this.employeeRepository.save(e);
        });
    }

    public EditEmployeeDTO getEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        EditEmployeeDTO editEmployeeDTO = mapper.map(employee.get(), EditEmployeeDTO.class);

        editEmployeeDTO.setRole(employee.get().getRole().getRoleType().name());
        editEmployeeDTO.setPassword("");

        return editEmployeeDTO;
    }

    public Optional<Employee> getByUsername(String username) {
        return this.employeeRepository.getByUsername(username);
    }

    public void editEmployee(Long employeeId, EditEmployeeDTO editEmployeeDTO) {

        // Throw duplication exception in case of unique constraint error for a different employee
        validateUsernameAndPhoneNumber(employeeId, editEmployeeDTO.getUsername(), editEmployeeDTO.getPhoneNumber());

        // Valid data
        Optional<Employee> employee = this.employeeRepository.findById(employeeId);
        Role role = this.roleRepository.findByRoleType(RoleType.valueOf(editEmployeeDTO.getRole()));

        employee.ifPresent(e -> {
            e.setFirstName(editEmployeeDTO.getFirstName());
            e.setLastName(editEmployeeDTO.getLastName());
            e.setUsername(editEmployeeDTO.getUsername());
            e.setPhoneNumber(editEmployeeDTO.getPhoneNumber());
            e.setRole(role);
            e.setHireDate(editEmployeeDTO.getHireDate());

            // Change password, only if the password field in the DTO is not empty
            if (!editEmployeeDTO.getPassword().isEmpty()) {
                e.setPassword(passwordEncoder.encode(editEmployeeDTO.getPassword()));
            }

            this.employeeRepository.saveAndFlush(e);
        });
    }

    //TODO: CALL BASE FUNCTION
    private void validateUsernameAndPhoneNumber(Long employeeId, String username, String phoneNumber) {
        Optional<Employee> employeeByUsername = this.employeeRepository.getByUsername(username);
        Optional<Employee> employeeByPhoneNumber = this.employeeRepository.getByPhoneNumber(phoneNumber);

        if (employeeByUsername.isPresent() && employeeByUsername.get().getId() != employeeId) {
            throw new UsernameDuplicationException(String.format("User with username %s, already exists!", username));
        }

        if (employeeByPhoneNumber.isPresent() && employeeByPhoneNumber.get().getId() != employeeId) {
            throw new PhoneNumberDuplicationException(String.format("User with phone number %s, already exists!", phoneNumber));
        }
    }

    private void validateUsernameAndPhoneNumber(String username, String phoneNumber) {
        Optional<Employee> employeeByUsername = this.employeeRepository.getByUsername(username);
        Optional<Employee> employeeByPhoneNumber = this.employeeRepository.getByPhoneNumber(phoneNumber);

        if (employeeByUsername.isPresent()) {
            throw new UsernameDuplicationException(String.format("User with username %s, already exists!", username));
        }

        if (employeeByPhoneNumber.isPresent()) {
            throw new PhoneNumberDuplicationException(String.format("User with phone number %s, already exists!", phoneNumber));
        }
    }

    public void initUsers() {
        if (initUsers) {
            List<AddEmployeeDTO> employees = InitUsersData.getEmployees();

            employees.forEach(this::saveEmployee);
        }
    }

    public void enableAccount(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        employee.ifPresent(e -> {
            e.setEnabled(true);
            this.employeeRepository.save(e);
        });
    }
}
