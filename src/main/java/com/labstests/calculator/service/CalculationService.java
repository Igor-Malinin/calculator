package com.labstests.calculator.service;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.dto.converter.CalculationDtoConverter;
import com.labstests.calculator.entity.CalculationEntity;
import com.labstests.calculator.exception.CalculationNotFoundException;
import com.labstests.calculator.repository.CalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final CalculationRepository calculationRepository;

    @Transactional
    public CalculationDto addCalculation(CreateCalculationDto dto) {
        CalculationEntity calculationEntity = CalculationDtoConverter.convertDtoToEntity(dto);

        calculationEntity = calculationRepository.save(calculationEntity);

        return CalculationDtoConverter.convertEntityToDto(calculationEntity);
    }

    @Transactional(readOnly = true)
    public CalculationDto getCalculationDtoById(String uuid) {
        CalculationEntity calculationEntity = getCalculationEntityById(uuid);

        return CalculationDtoConverter.convertEntityToDto(calculationEntity);
    }

    @Transactional(readOnly = true)
    public CalculationEntity getCalculationEntityById(String uuid) {
        return calculationRepository.findById(uuid)
                .orElseThrow(() -> new CalculationNotFoundException("Вычисление с id: " + uuid + " - не существует"));
    }
}
