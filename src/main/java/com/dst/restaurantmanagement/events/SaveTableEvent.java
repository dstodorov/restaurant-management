package com.dst.restaurantmanagement.events;

import org.springframework.context.ApplicationEvent;

public class SaveTableEvent extends ApplicationEvent {
    public SaveTableEvent(Object source) {
        super(source);
    }
}
