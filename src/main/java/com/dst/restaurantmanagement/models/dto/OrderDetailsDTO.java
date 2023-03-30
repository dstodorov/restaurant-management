package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDTO {
    private Long tableNumber;
    private String waiterName;
    private String currentTime;
    private Map<String, ItemDetails> items;
    private BigDecimal totalAmount;
}
