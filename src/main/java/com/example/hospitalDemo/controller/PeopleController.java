package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.People;
import com.example.hospitalDemo.repository.OperationsRepository;
import com.example.hospitalDemo.repository.PeopleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    @PersistenceContext
    private EntityManager entityManager;

    private PeopleRepository peopleRepository;
    @Autowired
    private OperationsRepository operationsRepository;

    @Autowired
    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    /*@Autowired
    public PeopleController(PeopleRepository peopleRepository,
                            OperationsRepository operationsRepository) {
        this.peopleRepository = peopleRepository;
        this.operationsRepository = operationsRepository;
    }*/

    @GetMapping
    public ResponseEntity<List<People>> getPeople() {
        return new ResponseEntity<>(peopleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{doctor}")
    public ResponseEntity<List<People>> getPeopleByParam(@PathVariable("doctor") Boolean doctor) {
        List<People> people = peopleRepository.findPeopleByParam(doctor);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createPeople(@RequestBody People people) {
        HttpStatus resultStatus;

        try {
            peopleRepository.save(people);
            resultStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            resultStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePeople(@PathVariable("id") Long peopleId, @RequestBody People people) {
        HttpStatus resultStatus;
        Optional<People> actualPeopleOptional = peopleRepository.findById(peopleId);

        if (actualPeopleOptional.isPresent()) {
            People actualPeople = actualPeopleOptional.get();
            actualPeople.setFirstName(people.getFirstName());
            actualPeople.setLastName(people.getLastName());
            actualPeople.setDoctor(people.getDoctor());

            try {
                peopleRepository.save(actualPeople);
                resultStatus = HttpStatus.OK;
            } catch (ConstraintViolationException exception) {
                resultStatus = HttpStatus.BAD_REQUEST;
            }
        } else {
            resultStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        HttpStatus resultStatus;
        Optional<People> peopleToDelete = peopleRepository.findById(id);
        //Query query = entityManager.createQuery(
          //      "delete from Operations o where (o.doctorId = :p) or (o.patientId = :p)");


        if (peopleToDelete.isPresent()) {
//            operationsRepository.deleteOperationById(id);
//            operationsRepository.deleteByDoctorId(id);
//            operationsRepository.deleteByPatientId(id);
            //int deletedCount = query.setParameter("p", id).executeUpdate();
            peopleRepository.delete(peopleToDelete.get());
            resultStatus = HttpStatus.OK;
        } else {
            resultStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(resultStatus);
    }


}
