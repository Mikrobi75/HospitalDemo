package com.example.hospitalDemo.service;

import com.example.hospitalDemo.repository.OperationsRepository;
import com.example.hospitalDemo.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OperationService {
    private final OperationsRepository operationsRepository;

    public OperationService(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    public void deleteOperationByPeopleId(Long id) {
        operationsRepository.deleteOperationById(id);
    }

}
