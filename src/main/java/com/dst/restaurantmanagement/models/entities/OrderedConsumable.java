package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.DishStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_ordered_dishes")
public class OrderedConsumable extends BaseEntity{
    @OneToOne
    private Consumable consumable;
    @Column(nullable = false)
    private LocalDateTime orderTime;
    @Enumerated
    private DishStatus status;
}
