package com.labstests.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(CalculatorApplication::main).with(TestCalculatorApplication.class).run(args);
	}

}
