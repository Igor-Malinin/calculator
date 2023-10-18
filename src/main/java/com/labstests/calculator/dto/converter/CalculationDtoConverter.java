package com.labstests.calculator.dto.converter;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.entity.CalculationEntity;

import java.util.UUID;

public class CalculationDtoConverter {

    public static CalculationEntity convertDtoToEntity(CreateCalculationDto dto) {
        CalculationEntity calculationEntity = new CalculationEntity();

        calculationEntity.setUuid(UUID.randomUUID().toString());
        calculationEntity.setFirstNum(dto.getFirstNum());
        calculationEntity.setFirstNumSystem(dto.getFirstNumSystem());
        calculationEntity.setSecondNum(dto.getSecondNum());
        calculationEntity.setSecondNumSystem(dto.getSecondNumSystem());
        calculationEntity.setOperation(dto.getOperation());
        calculationEntity.setDateTime(dto.getDateTime());

        return calculationEntity;
    }

    public static CalculationDto convertEntityToDto(CalculationEntity calculationEntity) {
        CalculationDto calculationDto = new CalculationDto();

        calculationDto.setId(calculationEntity.getUuid());
        calculationDto.setFirstNum(calculationEntity.getFirstNum());
        calculationDto.setFirstNumSystem(calculationEntity.getFirstNumSystem());
        calculationDto.setSecondNum(calculationEntity.getSecondNum());
        calculationDto.setSecondNumSystem(calculationEntity.getSecondNumSystem());
        calculationDto.setOperation(calculationEntity.getOperation());
        calculationDto.setDateTime(calculationEntity.getDateTime());

        return calculationDto;
    }
}
