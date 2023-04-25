package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.People;
import com.example.hospitalDemo.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final OperationsService operationService;

    public PeopleService(PeopleRepository peopleRepository, OperationsService operationService) {
        this.peopleRepository = peopleRepository;
        this.operationService = operationService;
    }

    public void delete (Long id) {
        Optional<People> peopleToDelete = peopleRepository.findById(id);

        if (peopleToDelete.isPresent()) {
            operationService.deleteOperationByPeopleId(id);
            peopleRepository.delete(peopleToDelete.get());
        }

    }
}
