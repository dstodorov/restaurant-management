package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.TableStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_tables")
public class RestaurantTable extends BaseEntity {
    @Column(nullable = false)
    private Integer seats;
    @Enumerated(EnumType.STRING)
    private TableStatus status;
}
