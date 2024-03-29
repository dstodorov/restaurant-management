package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.ItemType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_menu_items")
public class MenuItem extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;
    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity;
    @Column(name = "purchased_quantity", nullable = false)
    private Integer purchasedQuantity;
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Basic
    private Boolean wasted = false;
}
