package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.RoleType;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Role;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import com.dst.restaurantmanagement.repositories.RoleRepository;
import com.dst.restaurantmanagement.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Employee testEmployee;

    private Role testRole;

    @Autowired
    private UserDetailsService userDetailsService;

    private UserDetails adminUserDetails;


    @BeforeEach
    void setUp() {

        testRole = this.roleRepository.findByRoleType(RoleType.WAITER);

        testEmployee = Employee
                .builder()
                .firstName("Dimitar")
                .lastName("Todorov")
                .username("Multi")
                .password("TopSecret")
                .role(testRole)
                .hireDate(LocalDate.now().plusDays(1))
                .phoneNumber("123-456-7890")
                .enabled(true)
                .build();

        adminUserDetails = userDetailsService.loadUserByUsername("admin");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Save user - successful")
    void testSavingUserSuccessful() throws Exception {
        mockMvc.perform(post("/employees/add")
                        .param("firstName", "Dimitar")
                        .param("lastName", "Todorov")
                        .param("username", "multi")
                        .param("password", "secret")
                        .param("phoneNumber", "5108723409")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "WAITER")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        Optional<Employee> employee = this.employeeRepository.findByUsername("multi");

        Assertions.assertTrue(employee.isPresent());
        employee.ifPresent(e -> this.employeeRepository.deleteById(e.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Adding user with not valid input - failed")
    void testSavingUserInvalidInputFailed() throws Exception {
        mockMvc.perform(post("/employees/add")
                        .param("firstName", "Dimitar")
                        .param("lastName", "Todorov")
                        .param("username", "mu")
                        .param("password", "secret")
                        .param("phoneNumber", "5108723409")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "COOK")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Adding employee with duplicated username  - failed")
    void testSavingDuplicatedUserFailed() throws Exception {

        Employee savedTestEmployee = this.employeeRepository.save(testEmployee);

        mockMvc.perform(post("/employees/add")
                        .param("firstName", "Dimitar")
                        .param("lastName", "Todorov")
                        .param("username", "multi")
                        .param("password", "TopSecret")
                        .param("phoneNumber", "123-456-7890")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "COOK")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/add"));

        this.employeeRepository.delete(savedTestEmployee);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Edit employee - successful")
    void testEditUserSuccessful() throws Exception {

        Employee savedTestEmployee = this.employeeRepository.save(testEmployee);

        mockMvc.perform(post("/employees/edit/" + savedTestEmployee.getId())
                        .param("firstName", "Dimitar")
                        .param("lastName", "Todorov")
                        .param("username", "multi2")
                        .param("password", "")
                        .param("phoneNumber", "123-456-7890")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "WAITER")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andReturn();

        Optional<Employee> employeeByUsername = this.employeeRepository.findByUsername("multi2");

        Assertions.assertTrue(employeeByUsername.isPresent());

        employeeByUsername.ifPresent(e -> this.employeeRepository.deleteById(e.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Edit employee with invalid input - failed")
    void testEditUserInvalidInputFailed() throws Exception {

        Employee employee = this.employeeRepository.save(Employee
                .builder()
                .firstName("Magdalena")
                .lastName("Magdalenova")
                .username("magi02")
                .phoneNumber("120938102983")
                .password("topsecredpassword")
                .hireDate(LocalDate.now().plusDays(1))
                .enabled(true)
                .role(testRole)
                .build()
        );

        mockMvc.perform(post("/employees/edit/" + employee.getId())
                        .param("firstName", "")
                        .param("lastName", "testtest2")
                        .param("username", "username3")
                        .param("password", "")
                        .param("phoneNumber", "5381927509")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "WAITER")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/edit/" + employee.getId()));

        this.employeeRepository.deleteById(employee.getId());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Edit employee with duplicated username - failed")
    void testEditUserDuplicatedFailed() throws Exception {

        Employee savedTestEmployee = this.employeeRepository.save(this.testEmployee);

        Employee employee = this.employeeRepository.save(Employee
                .builder()
                .firstName("Magdalena")
                .lastName("Magdalenova")
                .username("magito12")
                .phoneNumber("1235125123")
                .password("topsecredpassword")
                .hireDate(LocalDate.now().plusDays(1))
                .enabled(true)
                .role(testRole)
                .build()
        );

        mockMvc.perform(post("/employees/edit/" + employee.getId())
                        .param("firstName", "tetete")
                        .param("lastName", "testtest2")
                        .param("username", "Multi")
                        .param("password", "topsecretpassword")
                        .param("phoneNumber", "5381927509")
                        .param("hireDate", LocalDate.now().plusDays(1).toString())
                        .param("role", "WAITER")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/edit/" + employee.getId()));

        this.employeeRepository.deleteById(savedTestEmployee.getId());
        this.employeeRepository.deleteById(employee.getId());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Successfully load employee page")
    void testOpenEmployeePageSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees").with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-employees"))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(content.contains("waiter"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Disable employee account - Successful")
    void testDisableEmployeeAccountSuccess() throws Exception {

        Optional<Employee> waiter = this.employeeRepository.findByUsername("waiter");

        mockMvc.perform(get("/employees/" + waiter.get().getId() +"/disable"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andReturn();

        Optional<Employee> updatedWaiter = this.employeeRepository.findByUsername("waiter");

        updatedWaiter.ifPresent(e -> Assertions.assertFalse(e.getEnabled()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Enable employee account - Successful")
    void testEnableEmployeeAccountSuccess() throws Exception {

        Optional<Employee> waiter = this.employeeRepository.findByUsername("waiter");
        waiter.ifPresent(e -> {
            e.setEnabled(false);

            Assertions.assertFalse(e.getEnabled());
        });

        mockMvc.perform(get("/employees/" + waiter.get().getId() +"/enable"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andReturn();

        Optional<Employee> updatedWaiter = this.employeeRepository.findByUsername("waiter");

        updatedWaiter.ifPresent(e -> Assertions.assertTrue(e.getEnabled()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Successfully load add employee page")
    void testOpenAddEmployeePageSuccess() throws Exception {
        mockMvc.perform(get("/employees/add").with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-new-employee"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Successfully load edit employee page")
    void testOpenEditEmployeePageSuccess() throws Exception {

        Optional<Employee> waiter = this.employeeRepository.findByUsername("waiter");

        MvcResult mvcResult = mockMvc.perform(get("/employees/edit/" + waiter.get().getId()).with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-edit-employee"))
                .andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("waiter"));


    }
}
