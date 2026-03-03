package com.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AsyncProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncProcessorApplication.class, args);
	}

}
