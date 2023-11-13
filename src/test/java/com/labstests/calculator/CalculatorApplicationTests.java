package com.labstests.calculator;

import com.labstests.calculator.utility.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Проверка операций калькулятора")
class CalculatorApplicationTests {

    Calculator calculator = new Calculator();

    @Nested
    @DisplayName("Операции над двоичными числами")
    class BinaryOperations {
        @ParameterizedTest
        @DisplayName("умножение")
        @CsvFileSource(resources = "/bin/binMult.csv")
        public void binMult(String num1, String num2, String result) {
            assertEquals(calculator.multiply(num1, num2, 2), result);
        }
        @ParameterizedTest
        @DisplayName("деление")
        @CsvFileSource(resources = "/bin/binDiv.csv")
        public void binDiv(String num1, String num2, String result) {
            assertEquals(calculator.divide(num1, num2, 2), result);
            //Деление на ноль
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide("1010", "0", 2);
            });
        }
        @ParameterizedTest
        @DisplayName("сложение")
        @CsvFileSource(resources = "/bin/binSum.csv")
        public void binSum(String num1, String num2, String result) {
            assertEquals(calculator.sum(num1, num2, 2), result);
        }
        @ParameterizedTest
        @DisplayName("вычитание")
        @CsvFileSource(resources = "/bin/binSubs.csv")
        public void binSubs(String num1, String num2, String result) {
            assertEquals(calculator.subtract(num1, num2, 2), result);
        }
    }

    @Nested
    @DisplayName("Операции над восьмиричными числами")
    class OctalOperations {
        @ParameterizedTest
        @DisplayName("умножение")
        @CsvFileSource(resources = "/oct/octMult.csv")
        public void octMult(String num1, String num2, String result) {
            assertEquals(calculator.multiply(num1, num2, 8), result);
        }
        @ParameterizedTest
        @DisplayName("деление")
        @CsvFileSource(resources = "/oct/octDiv.csv")
        public void octDiv(String num1, String num2, String result) {
            assertEquals(calculator.divide(num1, num2, 8), result);
            //Деление на ноль
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide("717", "0", 8);
            });
        }
        @ParameterizedTest
        @DisplayName("сложение")
        @CsvFileSource(resources = "/oct/octSum.csv")
        public void octSum(String num1, String num2, String result) {
            assertEquals(calculator.sum(num1, num2, 8), result);
        }
        @ParameterizedTest
        @DisplayName("вычитание")
        @CsvFileSource(resources = "/oct/octSubs.csv")
        public void octSubs(String num1, String num2, String result) {
            assertEquals(calculator.subtract(num1, num2, 8), result);
        }
    }

    @Nested
    @DisplayName("Операции над десятичными числами")
    class DecimalOperations {
        @ParameterizedTest
        @DisplayName("умножение")
        @CsvFileSource(resources = "/dec/decMult.csv")
        public void decMult(String num1, String num2, String result) {
            assertEquals(calculator.multiply(num1, num2, 10), result);
        }
        @ParameterizedTest
        @DisplayName("деление")
        @CsvFileSource(resources = "/dec/decDiv.csv")
        public void decDiv(String num1, String num2, String result) {
            assertEquals(calculator.divide(num1, num2, 10), result);
            //Деление на ноль
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide("298", "0", 10);
            });
        }
        @ParameterizedTest
        @DisplayName("сложение")
        @CsvFileSource(resources = "/dec/decSum.csv")
        public void decSum(String num1, String num2, String result) {
            assertEquals(calculator.sum(num1, num2, 10), result);
        }
        @ParameterizedTest
        @DisplayName("вычитание")
        @CsvFileSource(resources = "/dec/decSubs.csv")
        public void decSubs(String num1, String num2, String result) {
            assertEquals(calculator.subtract(num1, num2, 10), result);
        }
    }

    @Nested
    @DisplayName("Операции над шестнадцатиричными числами")
    class HexOperations {
        @ParameterizedTest
        @DisplayName("умножение")
        @CsvFileSource(resources = "/hex/hexMult.csv")
        public void hexMult(String num1, String num2, String result) {
            assertEquals(calculator.multiply(num1, num2, 16), result);
        }
        @ParameterizedTest
        @DisplayName("деление")
        @CsvFileSource(resources = "/hex/hexDiv.csv")
        public void hexDiv(String num1, String num2, String result) {
            assertEquals(calculator.divide(num1, num2, 16), result);
            //Деление на ноль
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide("f9a4", "0", 16);
            });
        }
        @ParameterizedTest
        @DisplayName("сложение")
        @CsvFileSource(resources = "/hex/hexSum.csv")
        public void hexSum(String num1, String num2, String result) {
            assertEquals(calculator.sum(num1, num2, 16), result);
        }
        @ParameterizedTest
        @DisplayName("вычитание")
        @CsvFileSource(resources = "/hex/hexSubs.csv")
        public void hexSubs(String num1, String num2, String result) {
            assertEquals(calculator.subtract(num1, num2, 16), result);
        }
    }
}
