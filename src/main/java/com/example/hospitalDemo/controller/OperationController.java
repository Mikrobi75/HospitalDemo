package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.Operation;
import com.example.hospitalDemo.dto.incoming.OperationCommand;
import com.example.hospitalDemo.dto.incoming.OperationUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.OperationDetail;
import com.example.hospitalDemo.dto.outgoing.OperationListItem;
import com.example.hospitalDemo.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation")
public class OperationController {
    private OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<List<OperationListItem>> getAllOperation() {
        return new ResponseEntity(operationService.findAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDetail> getOperationById(@PathVariable("id") Long id) {
        OperationDetail operationDetail = operationService.findOperationById(id);
        return new ResponseEntity<>(operationDetail,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createOperation(@RequestBody OperationCommand operationCommand) {
        operationService.saveOperation(operationCommand);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOperation(@PathVariable("id") Long operationId, @RequestBody OperationUpdateCommand operationUpdateCommand) {
        operationService.updateOperation(operationId, operationUpdateCommand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperation(@PathVariable("id") Long operationId) {
        operationService.deleteOperation(operationId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
