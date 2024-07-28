package com.example.clinicalsapi.clincalsapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.clinicalsapi.clincalsapi.models.Patient;
import com.example.clinicalsapi.clincalsapi.repo.PatientRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    @ResponseBody
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        logger.info("Fetched {} patients", patients.size());
        return patients;
    }

    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient with id {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            logger.info("Patient found: {}", patient.get());
        } else {
            logger.warn("Patient with id {} not found", id);
        }
        return patient;
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.info("Creating new patient: {}", patient);
        Patient savedPatient = patientRepository.save(patient);
        logger.info("Patient created with id {}", savedPatient.getId());
        return savedPatient;
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        logger.info("Updating patient with id {}", id);
        Patient patient = patientRepository.findById(id).orElseThrow(() -> {
            logger.error("Patient with id {} not found", id);
            return new RuntimeException("Patient not found");
        });
        patient.setFirstName(patientDetails.getFirstName());
        patient.setAge(patientDetails.getAge());
        patient.setLastName(patientDetails.getLastName());
        Patient updatedPatient = patientRepository.save(patient);
        logger.info("Patient with id {} updated", updatedPatient.getId());
        return updatedPatient;
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        logger.info("Deleting patient with id {}", id);
        Patient patient = patientRepository.findById(id).orElseThrow(() -> {
            logger.error("Patient with id {} not found", id);
            return new RuntimeException("Patient not found");
        });
        patientRepository.delete(patient);
        logger.info("Patient with id {} deleted", id);
    }
}