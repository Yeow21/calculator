package com.overtime.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
		// Calculator calculator = new Calculator(2024,4, 9, "operator", "A");
	}
}
