package com.example.clinicalsapi.clincalsapi.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "clinicaldata")
public class ClinicalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String componentName;
    private String componentValue;
    private Timestamp measuredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    @JsonIgnore    
    private Patient patient;
    
    // Constructors, getters, and setters
    public ClinicalData() {
    }

    public ClinicalData(String componentName, String componentValue, Timestamp measuredDateTime) {
        this.componentName = componentName;
        this.componentValue = componentValue;
        this.measuredDateTime = measuredDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    public Timestamp getMeasuredDateTime() {
        return measuredDateTime;
    }

    public void setMeasuredDateTime(Timestamp measuredDateTime) {
        this.measuredDateTime = measuredDateTime;
    }
    
    @Override
    public String toString() {
        return "ClinicalData{" +
                "id=" + id +
                ", componentName='" + componentName + '\'' +
                ", componentValue='" + componentValue + '\'' +
                ", measuredDateTime=" + measuredDateTime +
                '}';
    }
}