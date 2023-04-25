package com.example.hospitalDemo.service;

import com.example.hospitalDemo.repository.OperationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OperationsService {
    private final OperationsRepository operationsRepository;

    public OperationsService(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    public void deleteOperationByPeopleId(Long id) {
        operationsRepository.deleteOperationById(id);
    }

}
