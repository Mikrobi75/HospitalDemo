package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Patient;
import com.example.hospitalDemo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Patient findPatientById(Long id) {
        Patient patient = findById(id);
        return patient;
    }

    public List<Patient> findAllAccount() {
        return patientRepository.findAll();
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void updatePatient(Long patientId, Patient patient) {
        Patient actualPatient = findById(patientId);
        actualPatient.setFirstName(patient.getFirstName());
        actualPatient.setLastName(patient.getLastName());
        actualPatient.setTajNumber(patient.getTajNumber());
        patientRepository.save(actualPatient);
    }

    public void deletePatient(Long id) {
        patientRepository.delete(findById(id));
    }
}
