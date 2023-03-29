package com.example.nisum.webfluxmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class webfluxMongoDBApplication {

	public static void main(String[] args) {
		SpringApplication.run(webfluxMongoDBApplication.class, args);
	}

}
