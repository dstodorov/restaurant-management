package com.dst.restaurantmanagement.interceptors;

import com.dst.restaurantmanagement.models.entities.LogEntry;
import com.dst.restaurantmanagement.repositories.LogEntryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class AuditingInterceptor implements HandlerInterceptor {
    private final LogEntryRepository logEntryRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String url = request.getRequestURI();
            String method = request.getMethod();
            LocalDateTime timestamp = LocalDateTime.now();

            LogEntry logEntry = new LogEntry();
            logEntry.setUsername(username);
            logEntry.setUrl(url);
            logEntry.setMethod(method);
            logEntry.setTimestamp(timestamp);

            logEntryRepository.save(logEntry);
        }
    }
}
