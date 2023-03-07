package com.dst.restaurantmanagement.models.dto;

import com.dst.restaurantmanagement.enums.RoleType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AddEmployeeDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 5, max = 20)
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
