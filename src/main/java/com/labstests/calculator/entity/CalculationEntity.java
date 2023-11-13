package com.labstests.calculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "calculations", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
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
    @Column(name = "result")
    private String result;
    @Column(name = "dateTime")
    private String dateTime;
}
