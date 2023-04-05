package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.models.dto.AddMenuItemDTO;
import com.dst.restaurantmanagement.models.dto.AvailableMenuItemsDTO;
import com.dst.restaurantmanagement.models.dto.MenuItemDTO;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
import com.dst.restaurantmanagement.util.AppConstants;
import lombok.AllArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
                .getNonExpiringMenuItemsByType(LocalDate.now(), type)
                .stream()
                .sorted(Comparator.comparing(MenuItem::getExpiryDate))
                .filter(c -> c.getCurrentQuantity() >= AppConstants.MINIMUM_QUANTITY)
                .toList();
    }

    public void wasteMenuItem(Long id) {
        Optional<MenuItem> menuItemById = this.menuItemRepository.findById(id);

        menuItemById.ifPresent(menuItem -> {
            menuItem.setWasted(true);
            this.menuItemRepository.saveAndFlush(menuItem);
        });
    }

    public Map<String, List<MenuItemDTO>> getAvailableMenuItems() {
        Map<String, List<MenuItemDTO>> menuItems = new TreeMap<>();

        // Put all menu item types in to the menu
        Arrays.stream(ItemType.values()).forEach(t -> {
            menuItems.put(t.name(), new ArrayList<>());
        });

        // Add all menu items on their category in the menu
        this.menuItemRepository
                .getAvailableMenuItems(LocalDate.now())
                .forEach(menuItem -> {
                    String itemType = menuItem.getType().name();
                    if (menuItems.containsKey(itemType)) {
                        List<MenuItemDTO> items = menuItems.get(itemType);

                        // Remove item from the menu in case if there is the same item but with closer expiry date
                        // This is needed to avoid product duplication in the menu
                        items.removeIf(innerItem -> menuItem.getName().equals(innerItem.getName()) && menuItem.getExpiryDate().isBefore(innerItem.getExpiryDate()));

                        items.add(MenuItemDTO.
                                builder()
                                .id(menuItem.getId())
                                .name(menuItem.getName())
                                .expiryDate(menuItem.getExpiryDate())
                                .build());
                    }
                });

        return menuItems;

    }
}
