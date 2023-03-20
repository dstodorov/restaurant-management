package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_orders")
public class Order extends BaseEntity {
    private LocalDateTime orderTime;
    @OneToMany
    private List<OrderedConsumable> consumables;
    @ManyToOne
    private RestaurantTable table;
    @ManyToOne
    private Employee waiter;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
