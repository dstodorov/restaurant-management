package com.dst.restaurantmanagement.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.
//                authorizeHttpRequests().
//                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers("/", "/auth/login", "/users/add").anonymous();
////                .and()
////                .formLogin()
////                .loginPage("/auth/login");
//
//        return http.build();
//    }
}
