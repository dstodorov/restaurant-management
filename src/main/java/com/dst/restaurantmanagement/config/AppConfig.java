package com.dst.restaurantmanagement.config;

import com.dst.restaurantmanagement.interceptors.AuditingInterceptor;
import com.dst.restaurantmanagement.models.repositories.LogEntryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final AuditingInterceptor auditingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditingInterceptor);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
