package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.events.ChangeStatusEvent;
import com.dst.restaurantmanagement.models.entities.StatusLog;
import com.dst.restaurantmanagement.repositories.StatusLogRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusLogService {

    private final StatusLogRepository statusLogRepository;
    private final ModelMapper mapper;

    @EventListener(ChangeStatusEvent.class)
    public void writeLog(ChangeStatusEvent event) {

        StatusLog statusLog = mapper.map(event, StatusLog.class);

        statusLog.setId(0);

        this.statusLogRepository.save(statusLog);
    }

    public List<StatusLog> getAll() {
        return this.statusLogRepository.findAll();
    }
}
