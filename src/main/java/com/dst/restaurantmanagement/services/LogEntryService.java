package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.models.entities.LogEntry;
import com.dst.restaurantmanagement.repositories.LogEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class LogEntryService {
    private final LogEntryRepository logEntryRepository;

    public List<LogEntry> getAllLogs() {
        return this.logEntryRepository.findAll().stream().sorted(Comparator.comparing(LogEntry::getTimestamp).reversed()).toList();
    }
}
