package com.dimpon.magnolia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class,args);
    }




}
