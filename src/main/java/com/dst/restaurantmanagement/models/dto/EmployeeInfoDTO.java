package com.dst.restaurantmanagement.models.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private LocalDate hireDate;
    private Boolean enabled;
    private String role;
}
