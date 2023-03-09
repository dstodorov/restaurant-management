package com.dst.restaurantmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private LocalDate hireDate;
    private String role;
}
