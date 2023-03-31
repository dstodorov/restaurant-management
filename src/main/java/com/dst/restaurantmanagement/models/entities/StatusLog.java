package com.dst.restaurantmanagement.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_status_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusLog extends BaseEntity {
    private LocalDateTime dateTime;
    private String user;
    private String event;
    private String previousStatus;
    private String newStatus;
    private Long objectId;
}
