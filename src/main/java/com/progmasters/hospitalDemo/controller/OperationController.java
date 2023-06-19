package com.progmasters.hospitalDemo.controller;

import com.progmasters.hospitalDemo.dto.incoming.OperationCommand;
import com.progmasters.hospitalDemo.dto.incoming.OperationUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.OperationDetail;
import com.progmasters.hospitalDemo.dto.outgoing.OperationListItem;
import com.progmasters.hospitalDemo.service.OperationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@Slf4j
public class OperationController {
    private OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<List<OperationListItem>> getAllOperation(HttpServletRequest req) {
        log.info("Http request, GET /api/operations");
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(operationService.findAll(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDetail> getOperationById(@PathVariable("id") Long id, HttpServletRequest req) {
        log.info("Http request, GET /api/operations/{id}, variable:" + id);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            OperationDetail operationDetail = operationService.findOperationById(id);
            return new ResponseEntity<>(operationDetail, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createOperation(@Valid @RequestBody OperationCommand operationCommand, HttpServletRequest req) {
        log.info("Http request, POST /api/operations/{id}, body:" + operationCommand.toString());
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            operationService.saveOperation(operationCommand);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOperation(@PathVariable("id") Long operationId, @Valid @RequestBody OperationUpdateCommand operationUpdateCommand, HttpServletRequest req) {
        log.info("Http request, PUT /api/operations/{id}, body:" + operationUpdateCommand.toString() + ", variable: " + operationId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            operationService.updateOperation(operationId, operationUpdateCommand);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperation(@PathVariable("id") Long operationId, HttpServletRequest req) {
        log.info("Http request, DELETE /api/operations/{id}, variable" + operationId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            operationService.deleteOperation(operationId);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
