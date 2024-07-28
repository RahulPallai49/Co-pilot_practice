package com.example.clinicalsapi.clincalsapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clinicalsapi.clincalsapi.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}