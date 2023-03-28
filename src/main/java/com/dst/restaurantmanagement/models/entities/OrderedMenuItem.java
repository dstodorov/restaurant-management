package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.DishStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_ordered_menu_items")
public class OrderedMenuItem extends BaseEntity{
    @OneToOne
    private MenuItem menuItem;
    @Column(nullable = false)
    private LocalDateTime orderTime;
    @Enumerated(EnumType.STRING)
    private DishStatus status;
}
