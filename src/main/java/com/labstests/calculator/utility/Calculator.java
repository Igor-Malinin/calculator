package com.labstests.calculator.utility;

public class Calculator {

    public Calculator() {}

    public String multiply(String num1, String num2, int system) {
        return Integer.toString(Integer.parseInt(num1, system) * Integer.parseInt(num2, system));
    }

    public String divide(String num1, String num2, int system) {
        return Integer.toString(Integer.parseInt(num1, system) / Integer.parseInt(num2, system));
    }

    public String sum(String num1, String num2, int system) {
        return Integer.toString(Integer.parseInt(num1, system) + Integer.parseInt(num2, system));
    }

    public String subtract(String num1, String num2, int system) {
        return Integer.toString(Integer.parseInt(num1, system) - Integer.parseInt(num2, system));
    }
}