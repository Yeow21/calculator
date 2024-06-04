package com.overtime.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;


import java.util.Arrays;

/**
 * The class is flagged as a @RestController, meaning it is ready for use by Spring MVC to handle web requests.
 * '@GetMapping' maps / to the index() method. When invoked from a browser or by using curl on the command line,
 * the method returns pure text. That is because @RestController combines @Controller and @ResponseBody,
 * two annotations that results in web requests returning data rather than a view.
 */
@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
		Calculator calculator = new Calculator(2024,4, 9, "operator", "A");
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
