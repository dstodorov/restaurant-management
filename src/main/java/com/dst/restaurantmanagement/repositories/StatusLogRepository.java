package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.models.entities.StatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusLogRepository extends JpaRepository<StatusLog, Long> {
}
