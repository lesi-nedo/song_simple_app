package com.tai.nOleksiy.simpleApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableJpaRepositories("com.tai.nOleksiy.simpleApp.repo")
@EntityScan("com.tai.nOleksiy.simpleApp.entity")
@SpringBootApplication()
public class SimpleSpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(SimpleSpringBootApplication.class, args);
	}

}
