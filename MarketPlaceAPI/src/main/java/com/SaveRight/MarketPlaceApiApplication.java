package com.SaveRight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
@OpenAPIDefinition
@SpringBootApplication
public class MarketPlaceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlaceApiApplication.class, args);
	}

}
