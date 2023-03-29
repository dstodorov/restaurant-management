package com.dst.restaurantmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CookingItemDTO {
    private Long orderId;
    private String itemName;
    private Long orderedItemId;
}
