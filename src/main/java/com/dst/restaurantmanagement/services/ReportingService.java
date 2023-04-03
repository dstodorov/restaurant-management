package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.models.entities.Report;
import com.dst.restaurantmanagement.repositories.ReportingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportingService {
    private final ReportingRepository reportingRepository;

    public List<Report> getAll() {
        return reportingRepository.findAll().stream().sorted(Comparator.comparing(Report::getReportDate).reversed()).toList();
    }
}
