package com.dst.restaurantmanagement.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NotBlank
@Builder
public class OrderedItemDTO {
    private Long tableId;
    private Long orderedItemId;
    private String menuItemName;
}
