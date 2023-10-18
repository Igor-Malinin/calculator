package com.labstests.calculator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calculations", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "firstNum", nullable = false)
    private String firstNum;

    @Column(name = "firstNumSystem")
    private String firstNumSystem;

    @Column(name = "secondNum")
    private String secondNum;

    @Column(name = "secondNumSystem")
    private String secondNumSystem;

    @Column(name = "operation")
    private String operation;

    @Column(name = "dateTime")
    private String dateTime;


}
