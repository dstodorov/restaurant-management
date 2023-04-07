package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.dto.AddEmployeeDTO;
import com.dst.restaurantmanagement.models.dto.EditEmployeeDTO;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import com.dst.restaurantmanagement.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;

    private AddEmployeeDTO employeeDTO;
    private EditEmployeeDTO editEmployeeDTO;
    private Employee employeeToEdit;
    private Role employeeRole;


    @BeforeEach
    public void setUp() {
        employeeDTO = AddEmployeeDTO
                .builder()
                .username("cook")
                .password("password")
                .firstName("Dimitar")
                .lastName("Todorov")
                .phoneNumber("123-456-7890")
                .role(RoleType.COOK.name())
                .build();

        editEmployeeDTO = EditEmployeeDTO
                .builder()
                .firstName("Dimitar")
                .lastName("Todorov")
                .username("mitaka")
                .password("password")
                .phoneNumber("123-456-7890")
                .hireDate(LocalDate.now())
                .role(RoleType.WAITER.name())
                .build();

        employeeToEdit = Employee
                .builder()
                .firstName("New")
                .lastName("Name")
                .username("mitaka")
                .phoneNumber("0987-654-321")
                .role(new Role(RoleType.ADMIN))
                .hireDate(LocalDate.now())
                .build();
        employeeToEdit.setId(1L);

        employeeRole = new Role();
        employeeRole.setRoleType(RoleType.COOK);
    }

    @Test
    @DisplayName("Save Employee - Successful")
    public void testSaveEmployeeSuccess() {
        when(employeeRepository.getByUsername(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.getByPhoneNumber(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleType(any(RoleType.class))).thenReturn(employeeRole);

        Employee employee = new Employee();
        when(mapper.map(employeeDTO, Employee.class)).thenReturn(employee);

        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

        String result = employeeService.saveEmployee(employeeDTO);

        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Save Employee - Username already exists")
    public void testSaveEmployeeUsernameExists() {
        Employee existingEmployee = new Employee();
        when(employeeRepository.getByUsername(anyString())).thenReturn(Optional.of(existingEmployee));

        String result = employeeService.saveEmployee(employeeDTO);

        assertEquals("User with username cook, already exists!", result);
    }

    @Test
    @DisplayName("Save Employee - Phone number already exists")
    public void testSaveEmployeePhoneNumberExists() {
        when(employeeRepository.getByUsername(anyString())).thenReturn(Optional.empty());
        Employee existingEmployee = new Employee();
        when(employeeRepository.getByPhoneNumber(anyString())).thenReturn(Optional.of(existingEmployee));

        String result = employeeService.saveEmployee(employeeDTO);

        assertEquals("User with phone number 123-456-7890, already exists!", result);
    }

/*    @Test
    @DisplayName("Init Administrator - Successful")
    public void testInitAdministratorSuccessful() {
        Role adminRole = new Role();

        adminRole.setRoleType(RoleType.ADMIN);
        Employee admin = Employee
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .firstName("Dimitar")
                .lastName("Todorov")
                .phoneNumber("123-456-7890")
                .role(adminRole)
                .build();

        when(employeeRepository.findByUsername("admin")).thenReturn(Optional.of(admin));

        Employee actual = this.employeeRepository.save(admin);

        Assertions.assertEquals(admin.getUsername(), actual.getUsername());
    }*/

    @Test
    @DisplayName("Edit Employee - Successful")
    void editEmployeeTest() {

        Role role = new Role();
        role.setId(2L);
        role.setRoleType(RoleType.WAITER);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeToEdit));
        when(employeeRepository.getByUsername(editEmployeeDTO.getUsername())).thenReturn(Optional.empty());
        when(employeeRepository.getByPhoneNumber(editEmployeeDTO.getPhoneNumber())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleType(RoleType.valueOf(editEmployeeDTO.getRole()))).thenReturn(role);
        when(passwordEncoder.encode(editEmployeeDTO.getPassword())).thenReturn("encoded_password");

        String result = employeeService.editEmployee(1L, editEmployeeDTO);

        Assertions.assertNull(result);
        Assertions.assertEquals(editEmployeeDTO.getFirstName(), employeeToEdit.getFirstName());
        Assertions.assertEquals(editEmployeeDTO.getLastName(), employeeToEdit.getLastName());
        Assertions.assertEquals(editEmployeeDTO.getUsername(), employeeToEdit.getUsername());
        Assertions.assertEquals(editEmployeeDTO.getPhoneNumber(), employeeToEdit.getPhoneNumber());
        Assertions.assertEquals(role, employeeToEdit.getRole());
        Assertions.assertEquals(editEmployeeDTO.getHireDate(), employeeToEdit.getHireDate());
        Assertions.assertEquals("encoded_password", employeeToEdit.getPassword());
    }
}