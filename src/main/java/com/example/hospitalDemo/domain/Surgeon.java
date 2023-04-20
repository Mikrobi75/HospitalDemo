package com.example.hospitalDemo.domain;

import jakarta.persistence.*;
import jdk.jfr.Name;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Surgeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "beginofpractice")
    private LocalDate beginOfPractice;

    @OneToMany(mappedBy = "surgeon")
    private List<Operation> operations;


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

    public LocalDate getBeginOfPractice() {
        return beginOfPractice;
    }

    public void setBeginOfPractice(LocalDate beginOfPractice) {
        this.beginOfPractice = beginOfPractice;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
