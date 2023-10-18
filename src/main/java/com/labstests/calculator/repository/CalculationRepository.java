package com.labstests.calculator.repository;

import com.labstests.calculator.entity.CalculationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRepository extends JpaRepository<CalculationEntity, String> {
}
