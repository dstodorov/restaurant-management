package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Order;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import com.dst.restaurantmanagement.repositories.OrderRepository;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private UserDetails waiterDetails;

    private RestaurantTable restaurantTable;


    @BeforeEach
    void setUp() {
        waiterDetails = userDetailsService.loadUserByUsername("waiter");

        restaurantTable = new RestaurantTable();
        restaurantTable.setStatus(TableStatus.USED);
        restaurantTable.setSeats(4);


    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully load waiter service page")
    void testOpenWaiterServicePageSuccess() throws Exception {
        mockMvc.perform(get("/service").with(user(waiterDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("waiter-dashboard"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully load waiter pending tables page")
    void testOpenWaiterPendingTablesPageSuccess() throws Exception {
        mockMvc.perform(get("/service/pending").with(user(waiterDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("waiter-pending-tables"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully start service tables")
    void testStartServiceTableSuccess() throws Exception {

        RestaurantTable table = new RestaurantTable();
        table.setSeats(4);
        table.setStatus(TableStatus.PENDING);

        RestaurantTable savedTable = this.restaurantTableRepository.save(table);

        mockMvc.perform(get("/service/" + savedTable.getId() + "/take").with(user(waiterDetails)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/service/pending"))
                .andReturn();

        Optional<RestaurantTable> updatedTable = this.restaurantTableRepository.findById(savedTable.getId());
        List<RestaurantTable> restaurantTables = this.orderRepository.findAll().stream().filter(o -> o.getStatus().equals(OrderStatus.OPEN) && o.getTable().getId() == savedTable.getId()).map(Order::getTable).toList();

        Assertions.assertEquals(TableStatus.USED, updatedTable.get().getStatus());
        Assertions.assertTrue(restaurantTables.stream().anyMatch(t -> t.getId() == updatedTable.get().getId() && t.getStatus() == updatedTable.get().getStatus()));
        this.orderRepository.deleteAll();
        updatedTable.ifPresent(t -> this.restaurantTableRepository.deleteById(t.getId()));
    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully open add item to order page")
    void testOpenAddItemToOrderPageSuccess() throws Exception {
        RestaurantTable table = this.restaurantTableRepository.save(restaurantTable);
        Optional<Employee> employee = this.employeeRepository.findByUsername(waiterDetails.getUsername());

        Order order = Order.builder().menuItems(new ArrayList<>()).orderTime(LocalDateTime.now()).table(table).waiter(employee.get()).build();

        this.orderRepository.save(order);

        mockMvc.perform(get("/service/" + order.getId() + "/add").with(user(waiterDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("waiter-add-to-order"))
                .andReturn();


        this.orderRepository.deleteAll();
        this.restaurantTableRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully open preview order page")
    void testOpenPreviewOrderPageSuccess() throws Exception {
        RestaurantTable table = this.restaurantTableRepository.save(restaurantTable);
        Optional<Employee> employee = this.employeeRepository.findByUsername(waiterDetails.getUsername());

        Order order = Order.builder().menuItems(new ArrayList<>()).orderTime(LocalDateTime.now()).table(table).waiter(employee.get()).build();

        Order saveOrder = this.orderRepository.save(order);

        mockMvc.perform(get("/service/preview")
                        .with(user(waiterDetails))
                        .param("orderId", String.valueOf(saveOrder.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("waiter-close-order"))
                .andReturn();

        this.orderRepository.deleteAll();
        this.restaurantTableRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "waiter", roles = {"WAITER"})
    @DisplayName("Successfully close order")
    void testCloseOrderSuccess() throws Exception {
        RestaurantTable table = this.restaurantTableRepository.save(restaurantTable);
        Optional<Employee> employee = this.employeeRepository.findByUsername(waiterDetails.getUsername());

        Order order = Order.builder().menuItems(new ArrayList<>()).orderTime(LocalDateTime.now()).table(table).waiter(employee.get()).build();

        Order saveOrder = this.orderRepository.save(order);

        mockMvc.perform(get("/service/close")
                        .with(user(waiterDetails))
                        .param("orderId", String.valueOf(saveOrder.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/service"))
                .andReturn();

        Optional<Order> updateOrder = this.orderRepository.findById(saveOrder.getId());

        Assertions.assertEquals(updateOrder.get().getStatus(), OrderStatus.CLOSED);

        this.orderRepository.deleteAll();
        this.restaurantTableRepository.deleteAll();
    }

}