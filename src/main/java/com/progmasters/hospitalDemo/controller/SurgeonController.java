package com.progmasters.hospitalDemo.controller;

import com.progmasters.hospitalDemo.dto.incoming.SurgeonCommand;
import com.progmasters.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.progmasters.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.progmasters.hospitalDemo.service.SurgeonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surgeons")
@Slf4j
public class SurgeonController {
    private SurgeonService surgeonService;

    @Autowired
    public SurgeonController(SurgeonService surgeonService) {
        this.surgeonService = surgeonService;
    }

    @GetMapping
    public ResponseEntity<List<SurgeonListItem>> getAllSurgeon(HttpServletRequest req) {
        log.info("Http request, GET /api/surgeons");
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(surgeonService.findAll(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurgeonDetail> getSurgeonById(@PathVariable("id") Long id, HttpServletRequest req) {
        log.info("Http request, GET /api/surgeons/{id}, variable:" + id);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            SurgeonDetail surgeon = surgeonService.findSurgeonById(id);
            return new ResponseEntity<>(surgeon, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createSurgeon(@Valid @RequestBody SurgeonCommand surgeonCommand, HttpServletRequest req) {
        log.info("Http request, POST /api/surgeons/{id}, body:" + surgeonCommand.toString());
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            surgeonService.saveSurgeon(surgeonCommand);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSurgeon(@PathVariable("id") Long surgeonId, @Valid @RequestBody SurgeonUpdateCommand surgeonUpdateCommand, HttpServletRequest req) {
        log.info("Http request, PUT /api/surgeons/{id}, body:" + surgeonUpdateCommand.toString() + ", variable: " + surgeonId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            surgeonService.updateSurgeon(surgeonId, surgeonUpdateCommand);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSurgeon(@PathVariable("id") Long surgeonId, HttpServletRequest req) {
        log.info("Http request, DELETE /api/surgeons/{id}, variable" + surgeonId);
        if (req.getSession().getAttribute("userName") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            surgeonService.deleteSurgeon(surgeonId);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

}
