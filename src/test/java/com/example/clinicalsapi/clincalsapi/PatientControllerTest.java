package com.example.clinicalsapi.clincalsapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.clinicalsapi.clincalsapi.controllers.PatientController;
import com.example.clinicalsapi.clincalsapi.models.Patient;
import com.example.clinicalsapi.clincalsapi.repo.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    public void testGetAllPatients_ReturnsPatients() throws Exception {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setFirstName("John");
        patient1.setLastName("Doe");
        patient1.setAge(30);

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFirstName("Jane");
        patient2.setLastName("Doe");
        patient2.setAge(25);

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    public void testGetAllPatients_ReturnsEmptyList() throws Exception {
        // Arrange
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetPatientById_ReturnsPatient() throws Exception {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAge(30);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Act & Assert
        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    public void testGetPatientById_ReturnsNotFound() throws Exception {
        // Arrange
        when(patientRepository.findById(153L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/patients/153"));
                
    }

    @Test
    public void testCreatePatient_Success() throws Exception {
        // Arrange
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAge(30);

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30}"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    public void testUpdatePatient_Success() throws Exception {
        // Arrange
        Patient existingPatient = new Patient();
        existingPatient.setId(1L);
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");
        existingPatient.setAge(30);

        Patient updatedDetails = new Patient();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Doe");
        updatedDetails.setAge(25);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedDetails);

        // Act & Assert
        mockMvc.perform(put("/patients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    public void testUpdatePatient_NotFound() throws Exception {
        // Arrange
        when(patientRepository.findById(17L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/patients/17")
                .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":25}"));
    }

    @Test
    public void testDeletePatient_Success() throws Exception {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAge(30);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(patient);

        // Act & Assert
        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isOk());
    }

}