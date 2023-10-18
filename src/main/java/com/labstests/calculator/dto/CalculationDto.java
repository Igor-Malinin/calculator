package com.labstests.calculator.dto;

import lombok.Data;

@Data
public class CalculationDto {

    private String id;

    private String firstNum;

    private String firstNumSystem;

    private String secondNum;

    private String secondNumSystem;

    private String operation;

    private String dateTime;
}
