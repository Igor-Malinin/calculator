package com.labstests.calculator;

import com.labstests.calculator.utility.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class JUnit5DynamicTests {

    Calculator calculator = new Calculator();

    public ArrayList<String> fileReader(String path) throws IOException {
        FileReader fr = new FileReader(path);
        int i;
        String sb = "";
        ArrayList<String> aList = new ArrayList<>();
        while ((i = fr.read()) != -1) {
            if ((char)i != ',' && (char)i != '\n') {
                if ((char)i == ' ')
                    continue;
                sb += (char)i;
            }
            else {
                aList.add(sb);
                sb = "";
            }
        }
        aList.add(sb);

        return aList;
    }

    @TestFactory
    @DisplayName("Двоичное деление")
    public Collection<DynamicTest> binSum() throws Exception {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        ArrayList<String> aList = fileReader("/Users/ichigo/Documents/autoTestLabs/calculator/src/test/resources/bin/binDiv.csv");

        for (int j = 0; j < aList.size()/3; j++) {
            int finalJ = j;
            dynamicTests.add(
                    DynamicTest.dynamicTest(Integer.toString(j + 1),
                            () -> assertEquals(
                                    aList.get(finalJ * 3 + 2),
                                    calculator.divide(aList.get(finalJ * 3), aList.get(finalJ * 3 + 1), 2)
                            ))
            );
        }

        return dynamicTests;
    }

    @TestFactory
    @DisplayName("Восьмиричная разность")
    public Collection<DynamicTest> octSubs() throws Exception {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        ArrayList<String> aList = fileReader("/Users/ichigo/Documents/autoTestLabs/calculator/src/test/resources/oct/octSubs.csv");

        for (int j = 0; j < aList.size()/3; j++) {
            int finalJ = j;
            dynamicTests.add(
                    DynamicTest.dynamicTest(Integer.toString(j + 1),
                            () -> assertEquals(
                                    aList.get(finalJ * 3 + 2),
                                    calculator.subtract(aList.get(finalJ * 3), aList.get(finalJ * 3 + 1), 8)
                            ))
            );
        }

        return dynamicTests;
    }

    @TestFactory
    @DisplayName("Десятичное умножение")
    public Collection<DynamicTest> decMult() throws Exception {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        ArrayList<String> aList = fileReader("/Users/ichigo/Documents/autoTestLabs/calculator/src/test/resources/dec/decMult.csv");

        for (int j = 0; j < aList.size()/3; j++) {
            int finalJ = j;
            dynamicTests.add(
                    DynamicTest.dynamicTest(Integer.toString(j + 1),
                    () -> assertEquals(
                            aList.get(finalJ * 3 + 2),
                            calculator.multiply(aList.get(finalJ * 3), aList.get(finalJ * 3 + 1), 10)
                    ))
            );
        }

        return dynamicTests;
    }

    @TestFactory
    @DisplayName("Шестнадцатиричная сумма")
    public Collection<DynamicTest> hexSum() throws Exception {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        ArrayList<String> aList = fileReader("/Users/ichigo/Documents/autoTestLabs/calculator/src/test/resources/hex/hexSum.csv");

        for (int j = 0; j < aList.size()/3; j++) {
            int finalJ = j;
            dynamicTests.add(
                    DynamicTest.dynamicTest(Integer.toString(j + 1),
                    () -> assertEquals(
                            aList.get(finalJ * 3 + 2),
                            calculator.sum(aList.get(finalJ * 3), aList.get(finalJ * 3 + 1), 16)
                    ))
            );
        }

        return dynamicTests;
    }


}
