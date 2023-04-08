package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private UserDetails workerDetails;


    @BeforeEach
    void setUp() {
        workerDetails = userDetailsService.loadUserByUsername("warehouse_worker");
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Add menu item - successful")
    void testAddMenuItemSuccessful() throws Exception {
        mockMvc.perform(post("/inventory/add")
                        .param("name", "Coca-Cola")
                        .param("type", "DRINK")
                        .param("purchasePrice", "2.55")
                        .param("salePrice", "3.55")
                        .param("purchasedQuantity", "100")
                        .param("expiryDate", LocalDate.now().plusDays(10).toString())
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventory"));

        List<MenuItem> availableMenuItems = menuItemRepository.getAvailableMenuItems(LocalDate.now());

        Assertions.assertEquals(1, availableMenuItems.size());
        Assertions.assertEquals("Coca-Cola", availableMenuItems.get(0).getName());

        this.menuItemRepository.deleteById(availableMenuItems.get(0).getId());
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Add menu item with invalid input - successful")
    void testAddMenuItemInvalidDataFailed() throws Exception {
        mockMvc.perform(post("/inventory/add")
                        .param("name", "Coca-Cola")
                        .param("type", "DRINK")
                        .param("purchasePrice", "2.55")
                        .param("salePrice", "3.55")
                        .param("purchasedQuantity", "-100")
                        .param("expiryDate", LocalDate.now().plusDays(10).toString())
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventory/add"));
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Successfully load inventory page")
    void testOpenInventoryPageSuccess() throws Exception {
        mockMvc.perform(get("/inventory").with(user(workerDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-inventory"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Successfully load expiring items page")
    void testOpenExpiringItemsPageSuccess() throws Exception {
        mockMvc.perform(get("/inventory/expiring").with(user(workerDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-inventory-expiring"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Successfully load add item page")
    void testOpenAddItemPageSuccess() throws Exception {
        mockMvc.perform(get("/inventory/add").with(user(workerDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-inventory-add"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "warehouse_worker", roles = {"WAREHOUSE_WORKER"})
    @DisplayName("Successfully waste item")
    void testWasteItemSuccess() throws Exception {

        MenuItem menuItem = new MenuItem();
        menuItem.setCurrentQuantity(50);
        menuItem.setPurchasedQuantity(100);
        menuItem.setWasted(false);
        menuItem.setPurchasePrice(BigDecimal.valueOf(2.5));
        menuItem.setSalePrice(BigDecimal.valueOf(3.5));
        menuItem.setType(ItemType.DRINK);
        menuItem.setName("Coca-Cola");
        menuItem.setExpiryDate(LocalDate.now().minusDays(5));

        MenuItem savedItem = this.menuItemRepository.save(menuItem);

        mockMvc.perform(get("/inventory/"+ savedItem.getId() + "/waste"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventory/expiring"))
                .andReturn();

        Optional<MenuItem> itemById = this.menuItemRepository.findById(savedItem.getId());

        itemById.ifPresent(i -> Assertions.assertTrue(i.getWasted()));
    }

}