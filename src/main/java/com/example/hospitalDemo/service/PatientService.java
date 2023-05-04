package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Patient;
import com.example.hospitalDemo.dto.incoming.PatientCommand;
import com.example.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.PatientDetail;
import com.example.hospitalDemo.dto.outgoing.PatientListItem;
import com.example.hospitalDemo.dto.outgoing.SurgeonListItem;
import com.example.hospitalDemo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    private PatientRepository patientRepository;

    private OperationService operationService;

    @Autowired
    public PatientService(PatientRepository patientRepository, OperationService operationService) {
        this.operationService = operationService;
        this.patientRepository = patientRepository;
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public PatientDetail findPatientById(Long id) {
        Patient patient = findById(id);
        return (new PatientDetail(patient));
    }

    public List<PatientListItem> findAllAccount() {
        return patientRepository.findAll().stream().map(PatientListItem::new).collect(Collectors.toList());
    }

    public void savePatient(PatientCommand patientCommand) {
        patientRepository.save(new Patient(patientCommand));
    }

    public void updatePatient(Long patientId, PatientUpdateCommand patientUpdateCommand) {
        Patient actualPatient = findById(patientId);
        actualPatient.setFirstName(patientUpdateCommand.getFirstName());
        actualPatient.setLastName(patientUpdateCommand.getLastName());
        actualPatient.setTajNumber(patientUpdateCommand.getTajNumber());
        patientRepository.save(actualPatient);
    }

    public void deletePatient(Long id) {
        operationService.deleteOperationByPatientId(id);
        patientRepository.delete(findById(id));
    }
}
