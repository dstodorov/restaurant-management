package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableMenuItemsDTO {
    String type;
    List<MenuItemDTO> menuItems;
}