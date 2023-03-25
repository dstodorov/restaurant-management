package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.ConsumableType;
import com.dst.restaurantmanagement.models.dto.AddConsumableDTO;
import com.dst.restaurantmanagement.models.entities.Consumable;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.repositories.ConsumableRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Consumable> getAllConsumables() {

        // Comparators for sorting, first by expiry date, then by  Consumable type
        Comparator<Consumable> compareByConsumableType = Comparator.comparing(Consumable::getType);
        Comparator<Consumable> compareByExpiryDate = Comparator.comparing(Consumable::getExpiryDate);
        Comparator<Consumable> compareByName= Comparator.comparing(Consumable::getExpiryDate);

        Comparator<Consumable> compareByConsumableExpiryDateAndType = compareByExpiryDate.thenComparing(compareByConsumableType).thenComparing(compareByName);

        return this.consumableRepository.findAll().stream().sorted(compareByConsumableExpiryDateAndType).toList();
    }
}
