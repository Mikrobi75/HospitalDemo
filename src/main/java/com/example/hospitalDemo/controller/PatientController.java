package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.dto.incoming.PatientCommand;
import com.example.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.PatientDetail;
import com.example.hospitalDemo.dto.outgoing.PatientListItem;
import com.example.hospitalDemo.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDate.now;

@RestController
@RequestMapping("/api/patients")
@Slf4j
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientListItem>> getAllPatient() {
        log.info("Http request, GET /api/patient");
        return new ResponseEntity(patientService.findAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetail> getPatientById(@PathVariable("id") Long id) {
        log.info("Http request, GET /api/patient/{id}, variable:" + id);
        PatientDetail patientDetail = patientService.findPatientById(id);
        return new ResponseEntity<>(patientDetail,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createPatient(@Valid @RequestBody PatientCommand patientCommand) {
        log.info("Http request, POST /api/patient/{id}, body:" + patientCommand.toString());
        patientService.savePatient(patientCommand);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePatient(@PathVariable("id") Long patientId, @Valid @RequestBody PatientUpdateCommand patientUpdateCommand) {
        log.info("Http request, PUT /api/patient/{id}, body:" + patientUpdateCommand.toString() + ", variable: " + patientId);
        patientService.updatePatient(patientId, patientUpdateCommand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") Long patientId) {
        log.info("Http request, DELETE /api/patient/{id}, variable" + patientId);
        patientService.deletePatient(patientId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
