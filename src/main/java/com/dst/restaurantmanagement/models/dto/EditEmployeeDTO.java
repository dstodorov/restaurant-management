package com.dst.restaurantmanagement.models.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditEmployeeDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    private String password;
    @NotBlank
    private String phoneNumber;
    @Future
    private LocalDate hireDate;
    @NotBlank
    private String role;
}
