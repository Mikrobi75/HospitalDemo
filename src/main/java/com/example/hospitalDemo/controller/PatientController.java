package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.Operations;
import com.example.hospitalDemo.domain.Patient;
import com.example.hospitalDemo.repository.OperationsRepository;
import com.example.hospitalDemo.service.PatientService;
import com.example.hospitalDemo.service.PeopleService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity getAllPatient() {
        return new ResponseEntity(patientService.findAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPatientById(@PathVariable("id") Long id) {
        Patient patient = patientService.findPatientById(id);
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createPatient(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePatient(@PathVariable("id") Long patientId, @RequestBody Patient patient) {
        patientService.updatePatient(patientId, patient);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
