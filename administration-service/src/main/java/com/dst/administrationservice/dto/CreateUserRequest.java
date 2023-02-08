package com.dst.administrationservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String phoneNumber;

    private String userRole;
}
