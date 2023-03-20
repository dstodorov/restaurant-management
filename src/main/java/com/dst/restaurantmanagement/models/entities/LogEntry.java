package com.dst.restaurantmanagement.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_access_logs")
public class LogEntry extends BaseEntity {
    private String username;
    private String url;
    private String method;
    private LocalDateTime timestamp;
}
