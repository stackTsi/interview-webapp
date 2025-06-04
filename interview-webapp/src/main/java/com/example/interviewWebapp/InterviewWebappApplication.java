package com.example.interviewWebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class InterviewWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewWebappApplication.class, args);
	}

}
