package com.dst.restaurantmanagement.util;

import com.dst.restaurantmanagement.enums.EventType;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.events.ChangeStatusEvent;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventPublisher {
    private static ApplicationEventPublisher staticApplicationEventPublisher;

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        staticApplicationEventPublisher = applicationEventPublisher;
    }

    public static void publish(RMUserDetails userDetails, Long objectId, Object object, String eventName, String oldStatus, String newStatus) {
        ChangeStatusEvent event = new ChangeStatusEvent(object);

        event.setDateTime(LocalDateTime.now());
        event.setUser(userDetails.getUsername());
        event.setEvent(eventName);
        event.setPreviousStatus(oldStatus);
        event.setNewStatus(newStatus);
        event.setObjectId(objectId);

        staticApplicationEventPublisher.publishEvent(event);
    }
}
