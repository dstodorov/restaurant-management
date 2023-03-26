package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.ConsumableType;
import com.dst.restaurantmanagement.models.dto.AddConsumableDTO;
import com.dst.restaurantmanagement.models.entities.Consumable;
import com.dst.restaurantmanagement.repositories.ConsumableRepository;
import com.dst.restaurantmanagement.util.AppConstants;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsumableService {
    private final ConsumableRepository consumableRepository;
    private final ModelMapper modelMapper;

    public void saveConsumable(AddConsumableDTO addConsumableDTO) {
        Consumable consumable = modelMapper.map(addConsumableDTO, Consumable.class);

        // Set current quantity to the original purchase quantity
        consumable.setCurrentQuantity(addConsumableDTO.getPurchasedQuantity());

        this.consumableRepository.save(consumable);
    }

    public List<Consumable> getExpiringConsumables() {

        // Return all products which have expiry date closed to the current date and have more than 0 product left

        return this.consumableRepository
                .getExpiringConsumables(LocalDate.now().plusDays(AppConstants.EXPIRE_DAYS_WARNING))
                .stream()
                .sorted(Comparator.comparing(Consumable::getExpiryDate))
                .filter(c -> c.getCurrentQuantity() >= AppConstants.MINIMUM_QUANTITY)
                .toList();
    }

    public List<Consumable> getConsumablesByType(ConsumableType type) {
        return this.consumableRepository
                .getNonExpiringConsumablesByType(LocalDate.now().plusDays(AppConstants.EXPIRE_DAYS_WARNING), type)
                .stream()
                .sorted(Comparator.comparing(Consumable::getExpiryDate))
                .filter(c -> c.getCurrentQuantity() >= AppConstants.MINIMUM_QUANTITY)
                .toList();
    }
}
