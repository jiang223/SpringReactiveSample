package com.springboot.cloud.common.core;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCreateCacheAnnotation
public class ProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProduceApplication.class, args);
    }
}
