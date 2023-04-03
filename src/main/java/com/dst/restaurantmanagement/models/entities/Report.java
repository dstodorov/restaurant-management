package com.dst.restaurantmanagement.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Report extends BaseEntity {
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;
    @Column(name = "product_of_the_day", nullable = false)
    private String productOfTheDay;
    @Column(name = "most_used_table", nullable = false)
    private String mostUsedTable;
    @Column(name = "employee_of_the_day", nullable = false)
    private String employeeOfTheDay;
    @Column(name = "daily_turnover", nullable = false)
    private BigDecimal dailyTurnover;
    @Column(name = "daily_pure_profit", nullable = false)
    private BigDecimal dailyPureProfit;
}
