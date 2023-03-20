package com.dst.restaurantmanagement.initializers;

import com.dst.restaurantmanagement.models.dto.AddTableDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class InitRestaurantTablesData {
    public static List<AddTableDTO> getTables() {
        List<AddTableDTO> tableDTOList = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            tableDTOList.add(AddTableDTO.builder().seats(new Random().nextInt(6 - 2) + 2).build());
        }

        return Collections.unmodifiableList(tableDTOList);
    }
}
