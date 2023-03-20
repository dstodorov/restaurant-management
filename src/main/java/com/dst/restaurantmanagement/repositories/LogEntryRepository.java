package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.models.entities.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
