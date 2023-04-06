package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDTO {
    private Long employees;
    private Long tables;
    private Long menuItems;
    private Long orders;
    private Long waistedItems;
    private BigDecimal waistedItemsValue;
    private BigDecimal totalTurnover;
    private BigDecimal totalProfit;
}
