package com.anpe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.anpe.model"})
@EnableJpaRepositories(basePackages = {"com.anpe.repository"})
@ComponentScan(basePackages = {"com.anpe.service", "com.anpe.controller"})
@EnableConfigurationProperties
@EnableAutoConfiguration
public class AnpeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnpeBackendApplication.class, args);
	}
}
