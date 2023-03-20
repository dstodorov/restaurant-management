package com.dst.restaurantmanagement.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTableDTO {
    @Min(2)
    @Max(5)
    @NotNull
    private Integer seats;
}
