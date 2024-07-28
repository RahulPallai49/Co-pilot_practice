package com.example.clinicalsapi.clincalsapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinicalsapi.clincalsapi.models.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {
}