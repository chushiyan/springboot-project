package com.kennatech.coating.shop;

import com.kennatech.coating.utils.IdWorker;
import com.kennatech.coating.utils.JwtInterceptor;
import com.kennatech.coating.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}
