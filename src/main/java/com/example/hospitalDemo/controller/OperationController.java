package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.Operation;
import com.example.hospitalDemo.dto.incoming.OperationCommand;
import com.example.hospitalDemo.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operation")
public class OperationController {
    private OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity getAllOperation() {
        return new ResponseEntity(operationService.findAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOperationById(@PathVariable("id") Long id) {
        Operation operation = operationService.findOperationById(id);
        return new ResponseEntity<>(operation,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createOperation(@RequestBody OperationCommand operationCommand) {
        operationService.saveOperation(operationCommand);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOperation(@PathVariable("id") Long operationId, @RequestBody Operation operation) {
        operationService.updateOperation(operationId, operation);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperation(@PathVariable("id") Long operationId) {
        operationService.deleteOperation(operationId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
