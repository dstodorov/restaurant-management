package com.dst.restaurantmanagement.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_food_warehouse")
public class FoodWarehouse extends BaseEntity {
    @ManyToOne
    private Consumable consumable;
    @Column(nullable = false)
    private Integer quantity;
}
