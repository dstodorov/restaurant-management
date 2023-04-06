package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.models.dto.StatsDTO;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.models.entities.Report;
import com.dst.restaurantmanagement.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class StatisticsService {
    private final OrderRepository orderRepository;
    private final ReportingRepository reportingRepository;
    private final EmployeeRepository employeeRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderedMenuItemRepository orderedMenuItemRepository;


    @Scheduled(cron = "0 59 23 * * *")
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

    public StatsDTO getStats() {
        Long employees = this.employeeRepository.count();

        Long tables = this.restaurantTableRepository.count();

        Long menuItems = this.menuItemRepository.count();

        Long orders = this.orderRepository
                .findAll()
                .stream()
                .filter(o -> o.getStatus().equals(OrderStatus.CLOSED))
                .count();

        Long waistedItems = this.menuItemRepository
                .findAll()
                .stream()
                .filter(MenuItem::getWasted).mapToLong(MenuItem::getCurrentQuantity).sum();

        BigDecimal totalWaisted = this.menuItemRepository
                .findAll()
                .stream()
                .filter(MenuItem::getWasted)
                .map(item -> item.getPurchasePrice().multiply(BigDecimal.valueOf(item.getCurrentQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTurnover = this.orderRepository.getTotalTurnover();

        BigDecimal totalPureProfit = this.orderRepository.getTotalPureProfit();

        return StatsDTO
                .builder()
                .employees(employees)
                .tables(tables)
                .menuItems(menuItems)
                .orders(orders)
                .waistedItems(waistedItems)
                .waistedItemsValue(totalWaisted)
                .totalTurnover(totalTurnover)
                .totalProfit(totalPureProfit)
                .build();
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
