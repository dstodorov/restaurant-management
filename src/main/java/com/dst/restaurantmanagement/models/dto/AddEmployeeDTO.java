package com.dst.restaurantmanagement.models.dto;

import com.dst.restaurantmanagement.enums.RoleType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEmployeeDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank
    @Size(min = 5)
    private String password;
    @NotBlank
    private String phoneNumber;
    @Future
    private LocalDate hireDate;
    @NotBlank
    private String role;
}
