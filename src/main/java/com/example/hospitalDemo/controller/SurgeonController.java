package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.Surgeon;
import com.example.hospitalDemo.dto.incoming.SurgeonCommand;
import com.example.hospitalDemo.dto.incoming.SurgeonUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.SurgeonDetail;
import com.example.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.example.hospitalDemo.service.SurgeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surgeon")
public class SurgeonController {
    private SurgeonService surgeonService;

    @Autowired
    public SurgeonController(SurgeonService surgeonService) {
        this.surgeonService = surgeonService;
    }

    @GetMapping
    public ResponseEntity<List<SurgeonListItem>> getAllSurgeon() {
        return new ResponseEntity(surgeonService.findAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurgeonDetail> getSurgeonById(@PathVariable("id") Long id) {
        SurgeonDetail surgeon = surgeonService.findSurgeonById(id);
        return new ResponseEntity<>(surgeon,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createSurgeon(@RequestBody SurgeonCommand surgeonCommand) {
        surgeonService.saveSurgeon(surgeonCommand);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSurgeon(@PathVariable("id") Long surgeonId, @RequestBody SurgeonUpdateCommand surgeonUpdateCommand) {
        surgeonService.updateSurgeon(surgeonId, surgeonUpdateCommand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSurgeon(@PathVariable("id") Long surgeonId) {
        surgeonService.deleteSurgeon(surgeonId);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
