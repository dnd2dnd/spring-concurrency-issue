package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringConcurrencyIssueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConcurrencyIssueApplication.class, args);
	}

}
