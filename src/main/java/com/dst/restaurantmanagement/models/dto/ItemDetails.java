package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetails {
    private String name;
    private Integer count;
    private BigDecimal pricePerItem;
}
