package com.dst.restaurantmanagement.events;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChangeStatusEvent extends ApplicationEvent {

    private LocalDateTime dateTime;
    private String user;
    private String event;
    private String previousStatus;
    private String newStatus;
    private Long objectId;

    public ChangeStatusEvent(Object source) {
        super(source);
    }
}
