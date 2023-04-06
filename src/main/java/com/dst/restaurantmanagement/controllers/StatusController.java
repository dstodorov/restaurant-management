package com.dst.restaurantmanagement.controllers;

import com.dst.restaurantmanagement.models.entities.StatusLog;
import com.dst.restaurantmanagement.services.StatusLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@AllArgsConstructor
public class StatusController {

    private final StatusLogService statusLogService;

    @GetMapping
    public ResponseEntity<List<StatusLog>> getStatuses() {
        List<StatusLog> statuses = this.statusLogService.getAll().stream().sorted(Comparator.comparing(StatusLog::getDateTime).reversed()).toList();

        return ResponseEntity.ok(statuses);
    }
}
