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
                .firstName("Borislav")
                .lastName("Rumenov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("waiter")
                .password("1234")
                .role("WAITER")
                .phoneNumber("269780453")
                .build();

        AddEmployeeDTO waiter2 = AddEmployeeDTO.builder()
                .firstName("Kristina")
                .lastName("Rumenova")
                .hireDate(LocalDate.now().plusDays(10))
                .username("kristina93")
                .password("1234")
                .role("WAITER")
                .phoneNumber("172803756")
                .build();

        AddEmployeeDTO cook = AddEmployeeDTO.builder()
                .firstName("Petar")
                .lastName("Kirov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("cook")
                .password("1234")
                .role("COOK")
                .phoneNumber("324923407")
                .build();

        AddEmployeeDTO cook2 = AddEmployeeDTO.builder()
                .firstName("Vladimir")
                .lastName("Milushev")
                .hireDate(LocalDate.now().plusDays(10))
                .username("vladika")
                .password("1234")
                .role("COOK")
                .phoneNumber("815837906")
                .build();

        AddEmployeeDTO host = AddEmployeeDTO.builder()
                .firstName("Dobrina")
                .lastName("Dimova")
                .hireDate(LocalDate.now().plusDays(10))
                .username("host")
                .password("1234")
                .role("HOST")
                .phoneNumber("827749098")
                .build();

        AddEmployeeDTO host2 = AddEmployeeDTO.builder()
                .firstName("Svetozara")
                .lastName("Todorova")
                .hireDate(LocalDate.now().plusDays(10))
                .username("zara2019")
                .password("1234")
                .role("HOST")
                .phoneNumber("346683220")
                .build();

        AddEmployeeDTO manager = AddEmployeeDTO.builder()
                .firstName("Dimitar")
                .lastName("Todorov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("manager")
                .password("1234")
                .role("MANAGER")
                .phoneNumber("900354155")
                .build();

        AddEmployeeDTO manager2 = AddEmployeeDTO.builder()
                .firstName("Georgi")
                .lastName("Minkov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("guci_mi")
                .password("1234")
                .role("MANAGER")
                .phoneNumber("161419336")
                .build();

        AddEmployeeDTO warehouseWorker = AddEmployeeDTO.builder()
                .firstName("Ivan")
                .lastName("Dimitrov")
                .hireDate(LocalDate.now().plusDays(10))
                .username("warehouse_worker")
                .password("1234")
                .role("WAREHOUSE_WORKER")
                .phoneNumber("584785834")
                .build();

        AddEmployeeDTO warehouseWorker2 = AddEmployeeDTO.builder()
                .firstName("Maria")
                .lastName("Stefanova")
                .hireDate(LocalDate.now().plusDays(10))
                .username("mima2003")
                .password("1234")
                .role("WAREHOUSE_WORKER")
                .phoneNumber("620171527")
                .build();

        employeeDTOS.add(waiter);
        employeeDTOS.add(waiter2);
        employeeDTOS.add(cook);
        employeeDTOS.add(cook2);
        employeeDTOS.add(host);
        employeeDTOS.add(host2);
        employeeDTOS.add(manager);
        employeeDTOS.add(manager2);
        employeeDTOS.add(warehouseWorker);
        employeeDTOS.add(warehouseWorker2);

        return Collections.unmodifiableList(employeeDTOS);
    }
}
