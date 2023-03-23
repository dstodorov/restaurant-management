package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.Order;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.OrderRepository;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeService employeeService;
    private final RestaurantTableRepository restaurantTableRepository;

    public void startService(Long tableId, Principal user) {
        Optional<Employee> employee = this.employeeService.getByUsername(user.getName());
        Optional<RestaurantTable> table = this.restaurantTableRepository.findById(tableId);

        table.get().setStatus(TableStatus.USED);

        restaurantTableRepository.saveAndFlush(table.get());

        Order order = Order.builder().waiter(employee.get()).table(table.get()).orderTime(LocalDateTime.now()).consumables(new ArrayList<>()).status(OrderStatus.OPEN).build();

        this.orderRepository.save(order);
    }

    public List<Long> getCurrentUserUsedTablesIds(RMUserDetails userDetails) {
        return this.orderRepository.findAllByWaiterIdAndStatus(userDetails.getId(), OrderStatus.OPEN)
                .stream()
                .map(o -> o.getTable().getId())
                .toList();
    }
}
