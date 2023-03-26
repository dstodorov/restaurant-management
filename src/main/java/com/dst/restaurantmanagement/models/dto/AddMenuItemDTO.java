package com.dst.restaurantmanagement.models.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMenuItemDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank
    private String type;
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
    @NotNull
    @Positive
    private BigDecimal salePrice;
    @NotNull
    @Positive
    @Min(10)
    @Max(2000)
    private Integer purchasedQuantity;
    @Future
    private LocalDate expiryDate;
}
