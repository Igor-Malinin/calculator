package com.labstests.calculator.controller;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.repository.CalculationRepository;
import com.labstests.calculator.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculation")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping
    public CalculationDto addCalculation(@RequestBody CreateCalculationDto createCalculationDto) {
        return calculationService.addCalculation(createCalculationDto);
    }

    @GetMapping(value = "/{id}")
    public CalculationDto getCalculation(@PathVariable String id) {
        return calculationService.getCalculationDtoById(id);
    }
}
