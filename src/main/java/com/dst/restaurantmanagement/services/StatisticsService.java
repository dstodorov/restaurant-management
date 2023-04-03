package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.models.entities.Report;
import com.dst.restaurantmanagement.repositories.OrderRepository;
import com.dst.restaurantmanagement.repositories.ReportingRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class StatisticsService {
    private final OrderRepository orderRepository;
    private final ReportingRepository reportingRepository;


    @Scheduled(cron = "0 39 22 * * *")
    private void generateReport() {
        String productOfTheDay = getProductOfTheDay();
        String mostUsedTable = getMostUsedTable();
        String employeeOfTheDay = getEmployeeOfTheDay();
        BigDecimal dailyTurnover = getDailyTurnover();
        BigDecimal dailyPureProfit = getDailyPureProfit();

        Report report = Report.builder()
                .reportDate(LocalDate.now())
                .productOfTheDay(productOfTheDay)
                .mostUsedTable(mostUsedTable)
                .employeeOfTheDay(employeeOfTheDay)
                .dailyTurnover(dailyTurnover)
                .dailyPureProfit(dailyPureProfit)
                .build();

        this.reportingRepository.save(report);

        System.out.println("Report generated!");
    }

    private String getMostUsedTable() {
        Long mostUsedTable = this.orderRepository.getMostUsedTable();

        return mostUsedTable > 0 ? mostUsedTable.toString() : "N/A";
    }


    private String getProductOfTheDay() {
        String productName = this.orderRepository.getProductOfTheDay();

        return productName.length() > 0 ? productName : "N/A";
    }

    private String getEmployeeOfTheDay() {
        String employeeOfTheDay = this.orderRepository.getEmployeeOfTheDay();

        return employeeOfTheDay.length() > 0 ? employeeOfTheDay : "N/A";
    }

    private BigDecimal getDailyTurnover() {
        BigDecimal dailyTurnover = this.orderRepository.getDailyTurnover();

        return dailyTurnover.compareTo(BigDecimal.ZERO) > 0 ? dailyTurnover : BigDecimal.ZERO;
    }

    private BigDecimal getDailyPureProfit() {
        BigDecimal dailyTurnover = this.orderRepository.getDailyPureProfit();

        return dailyTurnover.compareTo(BigDecimal.ZERO) > 0 ? dailyTurnover : BigDecimal.ZERO;
    }
}
