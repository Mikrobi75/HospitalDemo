package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.PatientCommand;
import com.progmasters.hospitalDemo.dto.incoming.PatientUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.PatientDetail;
import com.progmasters.hospitalDemo.dto.outgoing.PatientListItem;
import com.progmasters.hospitalDemo.repository.PatientRepository;
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

    public List<PatientListItem> findAll() {
        return patientRepository.findAll().stream().map(PatientListItem::new).collect(Collectors.toList());
    }

    public Patient savePatient(PatientCommand patientCommand) {
        return patientRepository.save(new Patient(patientCommand));
    }

    public Patient updatePatient(Long patientId, PatientUpdateCommand patientUpdateCommand) {
        Patient actualPatient = findById(patientId);
        actualPatient.setFirstName(patientUpdateCommand.getFirstName());
        actualPatient.setLastName(patientUpdateCommand.getLastName());
        actualPatient.setTajNumber(patientUpdateCommand.getTajNumber());
        return patientRepository.save(actualPatient);
    }

    public void deletePatient(Long id) {
        operationService.deleteOperationByPatientId(id);
        patientRepository.delete(findById(id));
    }

    public List<Patient> findAllPatientAllData() {
        return patientRepository.findAll();
    }

}
