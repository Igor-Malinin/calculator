package com.labstests.calculator.dto.converter;

import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.entity.CalculationEntity;

public class CalculationDtoConverter {

    public static CalculationEntity convertDtoToEntity(CalculationDto dto) {
        CalculationEntity calculationEntity = new CalculationEntity();
        calculationEntity.setId(dto.getId());
        calculationEntity.setFirstNum(dto.getFirstNum());
        calculationEntity.setFirstNumSystem(dto.getFirstNumSystem());
        calculationEntity.setSecondNum(dto.getSecondNum());
        calculationEntity.setSecondNumSystem(dto.getSecondNumSystem());
        calculationEntity.setOperation(dto.getOperation());
        calculationEntity.setResult(dto.getResult());
        calculationEntity.setDateTime(dto.getDateTime());

        return calculationEntity;
    }

    public static CalculationDto convertEntityToDto(CalculationEntity calculationEntity) {
        CalculationDto calculationDto = new CalculationDto();

        calculationDto.setId(calculationEntity.getId());
        calculationDto.setFirstNum(calculationEntity.getFirstNum());
        calculationDto.setFirstNumSystem(calculationEntity.getFirstNumSystem());
        calculationDto.setSecondNum(calculationEntity.getSecondNum());
        calculationDto.setSecondNumSystem(calculationEntity.getSecondNumSystem());
        calculationDto.setOperation(calculationEntity.getOperation());
        calculationDto.setResult(calculationEntity.getResult());
        calculationDto.setDateTime(calculationEntity.getDateTime());

        return calculationDto;
    }
}
