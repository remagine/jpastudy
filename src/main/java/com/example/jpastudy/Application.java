package com.example.jpastudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configuration
@ComponentScan(
		basePackages = "com.example.jpastudy"
)
@EnableAutoConfiguration
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}