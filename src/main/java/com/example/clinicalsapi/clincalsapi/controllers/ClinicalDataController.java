package com.example.clinicalsapi.clincalsapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.clinicalsapi.clincalsapi.models.ClinicalData;
import com.example.clinicalsapi.clincalsapi.repo.ClinicalDataRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clinicaldata")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ClinicalData> getClinicalDataById(@PathVariable Long id) {
        return clinicalDataRepository.findById(id);
    }

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepository.save(clinicalData);
    }

    @PutMapping("/{id}")
    public ClinicalData updateClinicalData(@PathVariable Long id, @RequestBody ClinicalData clinicalDataDetails) {
        ClinicalData clinicalData = clinicalDataRepository.findById(id).orElseThrow();
        clinicalData.setComponentName(clinicalDataDetails.getComponentName());
        clinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
        clinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());
        return clinicalDataRepository.save(clinicalData);
    }

    @DeleteMapping("/{id}")
    public void deleteClinicalData(@PathVariable Long id) {
        ClinicalData clinicalData = clinicalDataRepository.findById(id).orElseThrow();
        clinicalDataRepository.delete(clinicalData);
    }
}