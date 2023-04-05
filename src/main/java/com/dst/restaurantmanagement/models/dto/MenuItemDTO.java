package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDTO {
    private Long id;
    private String name;
    private LocalDate expiryDate;
}
