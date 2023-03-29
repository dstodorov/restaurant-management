package com.dst.restaurantmanagement.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOpenOrderDTO {
    private Long tableId;
    private Long orderId;
    private Boolean canBeClosed = false;
}
