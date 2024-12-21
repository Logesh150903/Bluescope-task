package com.example.boot;


import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
		Logger logger = Logger.getLogger(BootApplication.class.getName());
		logger.info("Starting.............");
	}

}
