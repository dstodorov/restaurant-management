package com.dst.restaurantmanagement.models.entities;

import com.dst.restaurantmanagement.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_roles")
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

}
