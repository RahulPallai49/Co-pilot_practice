package com.example.clinicalsapi.clincalsapi.dto;

public class ClinicalDataRequest {
    private Long patientId;
    private String componentName;
    private String componentValue;

    public ClinicalDataRequest(Long patientId, String componentName, String componentValue) {
        this.patientId = patientId;
        this.componentName = componentName;
        this.componentValue = componentValue;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

}
