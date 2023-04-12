package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.Operations;
import com.example.hospitalDemo.domain.People;
import com.example.hospitalDemo.repository.OperationsRepository;
import com.example.hospitalDemo.repository.PeopleRepository;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@RestController
@RequestMapping("/api/operations")
public class OperationsController {
    private OperationsRepository operationsRepository;

    @Autowired
    public OperationsController(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Operations>> getOperations() {
        return new ResponseEntity<>(operationsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{operation}")
    public ResponseEntity<List<Operations>> getOperationsByParam(@PathVariable("operation") String operation) {
        List<Operations> operations = operationsRepository.findOperationsByType(operation);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createOperation(@RequestBody Operations operation) {
        HttpStatus resultStatus;
        Operations actualOperation = new Operations();

        actualOperation.setOperation(operation.getOperation());
        actualOperation.setDoctorId(operation.getDoctorId());
        actualOperation.setPatientId(operation.getPatientId());
        actualOperation.setOperationDate(now());

        try {
            operationsRepository.save(actualOperation);
            resultStatus = HttpStatus.OK;
        } catch (ConstraintViolationException exception) {
            resultStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        HttpStatus resultStatus;
        Optional<Operations> operationToDelete = operationsRepository.findById(id);

        if (operationToDelete.isPresent()) {
            operationsRepository.delete(operationToDelete.get());
            resultStatus = HttpStatus.OK;
        } else {
            resultStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(resultStatus);
    }


}
