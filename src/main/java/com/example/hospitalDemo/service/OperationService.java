package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Operation;
import com.example.hospitalDemo.domain.Patient;
import com.example.hospitalDemo.domain.Surgeon;
import com.example.hospitalDemo.dto.incoming.OperationCommand;
import com.example.hospitalDemo.dto.incoming.OperationUpdateCommand;
import com.example.hospitalDemo.dto.outgoing.OperationDetail;
import com.example.hospitalDemo.dto.outgoing.OperationListItem;
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
import java.util.stream.Collectors;

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

    public OperationDetail findOperationById(Long id) {
        Operation operation = findById(id);
        return (new OperationDetail(operation));
    }

    public List<OperationListItem> findAllAccount() {
        /*List<OperationListItem> operationList = new ArrayList<>();
        for(Operation operation : operationRepository.findAll()) {
            operationList.add(new OperationListItem(operation));
        }
        return operationList;*/
        return operationRepository.findAll().stream().map(OperationListItem::new).collect(Collectors.toList());

    }

    public void saveOperation(OperationCommand operationCommand) {
        Optional<Surgeon> surgeon = surgeonRepository.findById(operationCommand.getSurgeonId());
        Optional<Patient> patient = patientRepository.findById(operationCommand.getPatientId());
        Operation operation = modelMapper.map(operationCommand, Operation.class);
        operation.setSurgeon(surgeon.get());
        operation.setPatient(patient.get());
        operationRepository.save(operation);
    }

    public void updateOperation(Long operationId, OperationUpdateCommand operationUpdateCommand) {
        Operation actualOperation = findById(operationId);

        Optional<Surgeon> surgeon = surgeonRepository.findById(operationUpdateCommand.getSurgeonId());
        Optional<Patient> patient = patientRepository.findById(operationUpdateCommand.getPatientId());
        actualOperation.setOperatingRoom(operationUpdateCommand.getOperatingRoom());
        actualOperation.setOperationDate(operationUpdateCommand.getOperationDate());
        actualOperation.setSurgeon(surgeon.get());
        actualOperation.setPatient(patient.get());

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
