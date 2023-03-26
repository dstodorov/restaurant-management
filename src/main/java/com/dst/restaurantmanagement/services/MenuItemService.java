package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.models.dto.AddMenuItemDTO;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
import com.dst.restaurantmanagement.util.AppConstants;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public void saveMenuItem(AddMenuItemDTO addMenuItemDTO) {
        MenuItem menuItem = modelMapper.map(addMenuItemDTO, MenuItem.class);

        // Set current quantity to the original purchase quantity
        menuItem.setCurrentQuantity(addMenuItemDTO.getPurchasedQuantity());

        this.menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getExpiringMenuItems() {

        // Return all products which have expiry date closed to the current date and have more than 0 product left

        return this.menuItemRepository
                .getExpiringMenuItems(LocalDate.now().plusDays(AppConstants.EXPIRE_DAYS_WARNING))
                .stream()
                .sorted(Comparator.comparing(MenuItem::getExpiryDate))
                .filter(c -> c.getCurrentQuantity() >= AppConstants.MINIMUM_QUANTITY)
                .toList();
    }

    public List<MenuItem> getMenuItemsByType(ItemType type) {
        return this.menuItemRepository
                .getNonExpiringMenuItemsByType(LocalDate.now().plusDays(AppConstants.EXPIRE_DAYS_WARNING), type)
                .stream()
                .sorted(Comparator.comparing(MenuItem::getExpiryDate))
                .filter(c -> c.getCurrentQuantity() >= AppConstants.MINIMUM_QUANTITY)
                .toList();
    }
}
