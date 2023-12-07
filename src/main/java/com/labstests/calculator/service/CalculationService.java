package com.labstests.calculator.service;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.dto.converter.CalculationDtoConverter;
import com.labstests.calculator.entity.CalculationEntity;
import com.labstests.calculator.exception.CalculationNotFoundException;
import com.labstests.calculator.repository.CalculationRepository;
import com.labstests.calculator.utility.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final CalculationRepository calculationRepository;

    @Transactional
    public CalculationDto addCalculation(CalculationDto dto) {
        CalculationEntity calculationEntity = CalculationDtoConverter.convertDtoToEntity(dto);
        calculationEntity = calculationRepository.save(calculationEntity);
        return CalculationDtoConverter.convertEntityToDto(calculationEntity);
    }

    public CalculationDto getCalculationDtoById(String uuid) {
        CalculationEntity calculationEntity = getCalculationEntityById(uuid);
        return CalculationDtoConverter.convertEntityToDto(calculationEntity);
    }

    public CalculationEntity getCalculationEntityById(String uuid) {
        return calculationRepository.findById(uuid)
                .orElseThrow(() -> new CalculationNotFoundException("Вычисление с id: " + uuid + " - не существует"));
    }

    public List<CalculationDto> getAllCalculations() {
        List<CalculationEntity> calculationEntity = getAllCalculationsList();
        List<CalculationDto> calculationDtos = new ArrayList<>();
        for (CalculationEntity entity : calculationEntity) {
            calculationDtos.add(CalculationDtoConverter.convertEntityToDto(entity));
        }
        return calculationDtos;
    }

    public List<CalculationEntity> getAllCalculationsList() {
        return calculationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String getResult(String fn, String sn, String oper, int system) {
        Calculator calculator = new Calculator();
        String result = "";
        switch (oper) {
            case ("*") -> result = calculator.multiply(fn, sn, system);
            case ("÷") -> result = calculator.divide(fn, sn, system);
            case ("-") -> result = calculator.subtract(fn, sn, system);
            case ("+") -> result = calculator.sum(fn, sn, system);
        }
        return result;
    }
}
