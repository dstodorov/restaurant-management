package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.OrderStatus;
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
    @Column(nullable = false)
    private LocalDateTime orderTime;
    @Basic
    private LocalDateTime orderClosed;
    @OneToMany
    private List<OrderedMenuItem> menuItems;
    @ManyToOne
    private RestaurantTable table;
    @ManyToOne
    private Employee waiter;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
