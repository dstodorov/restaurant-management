package com.dst.restaurantmanagement.initializers;

import com.dst.restaurantmanagement.models.dto.AddEmployeeDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InitUsersData {
    public static List<AddEmployeeDTO> getEmployees() {
        List<AddEmployeeDTO> employeeDTOS = new ArrayList<>();

        AddEmployeeDTO waiter = AddEmployeeDTO.builder()
                .firstName("Waiter")
                .lastName("Waiterov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("waiter")
                .password("1234")
                .role("WAITER")
                .phoneNumber("12345")
                .build();

        AddEmployeeDTO cook = AddEmployeeDTO.builder()
                .firstName("Cook")
                .lastName("Cookerov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("cook")
                .password("1234")
                .role("COOK")
                .phoneNumber("123456")
                .build();

        AddEmployeeDTO host = AddEmployeeDTO.builder()
                .firstName("Host")
                .lastName("Hostov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("host")
                .password("1234")
                .role("HOST")
                .phoneNumber("1234567")
                .build();

        AddEmployeeDTO manager = AddEmployeeDTO.builder()
                .firstName("Manager")
                .lastName("Managerov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("manager")
                .password("1234")
                .role("MANAGER")
                .phoneNumber("12345678")
                .build();

        AddEmployeeDTO warehouseWorker = AddEmployeeDTO.builder()
                .firstName("House")
                .lastName("Worker")
                .hireDate(LocalDate.now().plusDays(10))
                .username("warehouse_worker")
                .password("1234")
                .role("WAREHOUSE_WORKER")
                .phoneNumber("123456789")
                .build();

        employeeDTOS.add(waiter);
        employeeDTOS.add(cook);
        employeeDTOS.add(host);
        employeeDTOS.add(manager);
        employeeDTOS.add(warehouseWorker);

        return Collections.unmodifiableList(employeeDTOS);
    }
}
