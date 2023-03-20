package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.events.SaveTableEvent;
import com.dst.restaurantmanagement.initializers.InitRestaurantTablesData;
import com.dst.restaurantmanagement.models.dto.AddTableDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("#{new Boolean('${app.init.tables}')}")
    private Boolean initTables;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void saveTable(AddTableDTO addTableDTO) {
        RestaurantTable table = new RestaurantTable();
        table.setSeats(addTableDTO.getSeats());
        table.setStatus(TableStatus.FREE);

        this.restaurantTableRepository.save(table);

        //applicationEventPublisher.publishEvent(new SaveTableEvent(this));
    }

    public List<RestaurantTable> getTables() {
        return this.restaurantTableRepository.findAllByStatus(TableStatus.FREE);
    }

    public List<RestaurantTable> getTables(Integer seats) {
        return this.restaurantTableRepository.findAllBySeatsAndStatus(seats, TableStatus.FREE);
    }

    public void deleteTable(Long id) {
        this.restaurantTableRepository.deleteById(id);
    }

    public void accommodateTable(Long id) {
        Optional<RestaurantTable> restaurantTable = this.restaurantTableRepository.findById(id);

        restaurantTable.ifPresent(table -> {
            table.setStatus(TableStatus.PENDING);
            this.restaurantTableRepository.saveAndFlush(table);
        });
    }

    public void initTables() {

        if (initTables) {
            List<AddTableDTO> tables = InitRestaurantTablesData.getTables();

            tables.forEach(this::saveTable);
        }
    }
}

