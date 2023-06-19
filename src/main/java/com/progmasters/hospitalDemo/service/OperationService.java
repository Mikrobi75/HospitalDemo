package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.Operation;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import com.progmasters.hospitalDemo.dto.incoming.OperationCommand;
import com.progmasters.hospitalDemo.dto.incoming.OperationUpdateCommand;
import com.progmasters.hospitalDemo.dto.outgoing.OperationDetail;
import com.progmasters.hospitalDemo.dto.outgoing.OperationListItem;
import com.progmasters.hospitalDemo.exceptionhandling.PatientNotFoundException;
import com.progmasters.hospitalDemo.exceptionhandling.SurgeonNotFoundException;
import com.progmasters.hospitalDemo.repository.OperationRepository;
import com.progmasters.hospitalDemo.repository.PatientRepository;
import com.progmasters.hospitalDemo.repository.SurgeonRepository;
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

    public List<OperationListItem> findAll() {
        /*List<OperationListItem> operationList = new ArrayList<>();
        for(Operation operation : operationRepository.findAll()) {
            operationList.add(new OperationListItem(operation));
        }
        return operationList;*/
        return operationRepository.findAll().stream().map(OperationListItem::new).collect(Collectors.toList());

    }

    public Operation saveOperation(OperationCommand operationCommand) {
        Optional<Surgeon> surgeon = surgeonRepository.findById(operationCommand.getSurgeonId());
        Optional<Patient> patient = patientRepository.findById(operationCommand.getPatientId());
        if (!surgeon.isPresent()) {
            throw new SurgeonNotFoundException(operationCommand.getSurgeonId());
        }
        if (!patient.isPresent()) {
            throw new PatientNotFoundException(operationCommand.getPatientId());
        }
        Operation operation = modelMapper.map(operationCommand, Operation.class);
        operation.setSurgeon(surgeon.get());
        operation.setPatient(patient.get());
        return operationRepository.save(operation);
    }

    public Operation updateOperation(Long operationId, OperationUpdateCommand operationUpdateCommand) {
        Operation actualOperation = findById(operationId);

        Optional<Surgeon> surgeon = surgeonRepository.findById(operationUpdateCommand.getSurgeonId());
        Optional<Patient> patient = patientRepository.findById(operationUpdateCommand.getPatientId());
        if (!surgeon.isPresent()) {
            throw new SurgeonNotFoundException(operationUpdateCommand.getSurgeonId());
        }
        if (!patient.isPresent()) {
            throw new PatientNotFoundException(operationUpdateCommand.getPatientId());
        }
        actualOperation.setOperatingRoom(operationUpdateCommand.getOperatingRoom());
        actualOperation.setOperationDate(operationUpdateCommand.getOperationDate());
        actualOperation.setSurgeon(surgeon.get());
        actualOperation.setPatient(patient.get());

        return operationRepository.save(actualOperation);
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

    public List<Operation> findAllOperationAllData() {
        return operationRepository.findAll();
    }

}
