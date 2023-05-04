package com.example.hospitalDemo.domain;

import com.example.hospitalDemo.dto.incoming.PatientCommand;
import com.example.hospitalDemo.dto.incoming.SurgeonCommand;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "tajnumber")
    private String tajNumber;

    @OneToMany(mappedBy = "patient")
    private List<Operation> operations;

    public Patient() {

    }

    public Patient (PatientCommand patientCommand) {
        this.firstName = patientCommand.getFirstName();
        this.lastName = patientCommand.getLastName();
        this.tajNumber = patientCommand.getTajNumber();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTajNumber() {
        return tajNumber;
    }

    public void setTajNumber(String tajNumber) {
        this.tajNumber = tajNumber;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
