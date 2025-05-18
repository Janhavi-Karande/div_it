package com.example.expensesharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ExpenseSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseSharingApplication.class, args);
	}

}
