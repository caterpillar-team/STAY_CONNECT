package com.caterpillars.StayConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StayConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayConnectApplication.class, args);
	}

}
