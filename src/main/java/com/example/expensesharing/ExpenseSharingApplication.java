package com.example.expensesharing;

import com.example.expensesharing.commands.CommandExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@EnableJpaAuditing
@SpringBootApplication
public class ExpenseSharingApplication {

		public static void main(String[] args) {
			ApplicationContext context = SpringApplication.run(ExpenseSharingApplication.class, args);

			// Get CommandExecutor from Spring context
			CommandExecutor commandExecutor = context.getBean(CommandExecutor.class);

			Scanner scanner = new Scanner(System.in);
			String answer = "y";

			while (answer.equalsIgnoreCase("y")) {

				System.out.println("Please enter the command: ");
				String input = scanner.nextLine();
				commandExecutor.execute(input);

				System.out.println("Do you want to continue giving commands? (y/n)");
				answer = scanner.nextLine();
			}
		}
}



