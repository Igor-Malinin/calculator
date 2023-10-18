package com.labstests.calculator;

import com.labstests.calculator.utility.Calculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CalculatorApplication {

    static String checker(Scanner scanner) {
        String num;
        while (true) {
            try {
                num = scanner.nextLine();
                Integer.parseInt(num);
                break;
            } catch (InputMismatchException mme) {
                System.out.println("Ошибка, пожалуйста введите число.");
                scanner.nextLine();
            }
        }
        return num;
    }

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);

        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        int system;
        String num1;
        String num2;
        String operation;
        String result = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        System.out.println(formatter.format(new Date()));
        System.out.println("Введите систему счисления (2, 8, 10, 16): ");
        while ((system = scanner.nextInt()) != 2 && system != 8 && system != 10 && system != 16) {
            System.out.println("Введите повторно систему счисления (2, 8, 10, 16): ");
        }
        System.out.println("Введите первое число: ");
        num1 = checker(scanner);
        System.out.println("Введите второе число: ");
        num2 = checker(scanner);
        System.out.println("Введите операцию (*,/,+,-): ");
        operation = scanner.next();

        switch (operation) {
            case ("*") -> result = calculator.multiply(num1, num2, system);
            case ("/") -> result = calculator.divide(num1, num2, system);
            case ("+") -> result = calculator.sum(num1, num2, system);
            case ("-") -> result = calculator.subtract(num1, num2, system);
        }

        System.out.println("Результат: " + result);
    }

}

//        for (int i = 0; i < 20; i++) {
//            num1 = rand.nextInt(1000);
//            num2 = rand.nextInt(1000);
//
//            calculator.divideBinary(Integer.toOctalString(num1), Integer.toOctalString(num2));
//        }