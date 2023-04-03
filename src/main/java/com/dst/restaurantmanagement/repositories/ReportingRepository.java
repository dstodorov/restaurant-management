package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.models.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingRepository extends JpaRepository<Report, Long> {
}
