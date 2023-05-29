package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.dto.incoming.PatientCommand;
import com.example.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.PatientDetail;
import com.example.hospitalDemo.dto.outgoing.PatientListItem;
import com.example.hospitalDemo.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<PatientListItem>> getAllPatient(HttpServletRequest req) {
        log.info("Http request, GET /api/patients");
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(patientService.findAllAccount(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetail> getPatientById(@PathVariable("id") Long id, HttpServletRequest req) {
        log.info("Http request, GET /api/patients/{id}, variable:" + id);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            PatientDetail patientDetail = patientService.findPatientById(id);
            return new ResponseEntity<>(patientDetail, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createPatient(@Valid @RequestBody PatientCommand patientCommand, HttpServletRequest req) {
        log.info("Http request, POST /api/patients/{id}, body:" + patientCommand.toString());
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            patientService.savePatient(patientCommand);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePatient(@PathVariable("id") Long patientId, @Valid @RequestBody PatientUpdateCommand patientUpdateCommand, HttpServletRequest req) {
        log.info("Http request, PUT /api/patients/{id}, body:" + patientUpdateCommand.toString() + ", variable: " + patientId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            patientService.updatePatient(patientId, patientUpdateCommand);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") Long patientId, HttpServletRequest req) {
        log.info("Http request, DELETE /api/patients/{id}, variable" + patientId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            patientService.deletePatient(patientId);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

}
