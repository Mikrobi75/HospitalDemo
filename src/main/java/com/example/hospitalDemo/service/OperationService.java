package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Operation;
import com.example.hospitalDemo.domain.Operations;
import com.example.hospitalDemo.domain.Patient;
import com.example.hospitalDemo.domain.Surgeon;
import com.example.hospitalDemo.dto.incoming.OperationCommand;
import com.example.hospitalDemo.repository.OperationRepository;
import com.example.hospitalDemo.repository.PatientRepository;
import com.example.hospitalDemo.repository.SurgeonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OperationService {
    private OperationRepository operationRepository;
    private SurgeonRepository surgeonRepository;
    private PatientRepository patientRepository;

    private ModelMapper modelMapper;

    //@Autowired
    //private PatientService patientService;

    @Autowired
    public OperationService(OperationRepository operationRepository, SurgeonRepository surgeonRepository, PatientRepository patientRepository, ModelMapper modelMapper) {
        this.operationRepository = operationRepository;
        this.surgeonRepository = surgeonRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public Operation findById(Long id) {
        return operationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Operation findOperationById(Long id) {
        Operation operation = findById(id);
        return operation;
    }

    public List<Operation> findAllAccount() {
        return operationRepository.findAll();
    }

    public void saveOperation(OperationCommand operationCommand) {
        /*Optional<Surgeon> surgeon = surgeonRepository.findById(operationCommand.getSurgeonId());
        Optional<Patient> patient = patientRepository.findById(operationCommand.getPatientId());
        Operation operation = new Operation();
        operation.setOperationDate(operationCommand.getOperationDate());
        operation.setOperatingRoom(operationCommand.getOperatingRoom());
        operation.setSurgeon(surgeon.get());
        operation.setPatient(patient.get());*/
        Operation operation = modelMapper.map(operationCommand, Operation.class);
        operationRepository.save(operation);
    }

    public void updateOperation(Long operationId, Operation operation) {
        Operation actualOperation = findById(operationId);
        actualOperation.setOperatingRoom(operation.getOperatingRoom());
        actualOperation.setOperationDate(operation.getOperationDate());
        actualOperation.setPatient(operation.getPatient());
        actualOperation.setSurgeon(operation.getSurgeon());

        operationRepository.save(actualOperation);
    }

    public void deleteOperation(Long id) {
        operationRepository.delete(findById(id));
    }

    public void deleteOperationBySurgeonId(Long id) {
        operationRepository.deleteOperationBySurgeonId(id);
    }

    public void deleteOperationByPatientId(Long id) {
        operationRepository.deleteOperationByPatientId(id);
    }
}
