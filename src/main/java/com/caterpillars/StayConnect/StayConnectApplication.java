package com.caterpillars.StayConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:./.env")
public class StayConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StayConnectApplication.class, args);
    }
}
