package com.labstests.calculator.controller;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping("/calculation")
    public CalculationDto addCalculation(@RequestBody CalculationDto createCalculationDto) {
        return calculationService.addCalculation(createCalculationDto);
    }

    @GetMapping("/calculation/{id}")
    public CalculationDto getCalculation(@PathVariable String id) {
        return calculationService.getCalculationDtoById(id);
    }

    @GetMapping("/calculations")
    public List<CalculationDto> getAllCalculations() {
        return calculationService.getAllCalculations();
    }

    @GetMapping("/result/{fn}&{sn}&{oper}&{system}")
    public String getResult(
            @PathVariable("fn") String fn,
            @PathVariable("sn") String sn,
            @PathVariable("oper") String oper,
            @PathVariable("system") int system
    ) {
        return calculationService.getResult(fn, sn, oper, system);
    }
}
