package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.ConsumableType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_consumables")
public class Consumable extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private ConsumableType type;
    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;
}
