package com.dst.administrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String phoneNumber;

    private String userRole;
}
