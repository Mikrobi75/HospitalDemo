package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.Operation;
import com.example.hospitalDemo.repository.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OperationService {
    private OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
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

    public void saveOperation(Operation operation) {
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
}
