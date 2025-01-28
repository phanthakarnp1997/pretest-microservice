package com.example.microservice.test.pre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "CRM Issue Support API", version = "1.0", description = "Documentation for CRM Issue Support APIs"))
@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
