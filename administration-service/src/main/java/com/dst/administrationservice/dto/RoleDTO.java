package com.dst.administrationservice.dto;

import com.dst.administrationservice.models.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private UserRoles role;

    @Override
    public String toString() {
        return this.role.name();
    }
}
