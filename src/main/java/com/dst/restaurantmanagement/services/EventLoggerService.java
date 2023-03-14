package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.events.SaveTableEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class EventLoggerService {

    @EventListener(SaveTableEvent.class)
    public void tablesRefreshListener() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        attr.getRequest().getSession().setAttribute("manageTablesRefresh", true);
    }
}
