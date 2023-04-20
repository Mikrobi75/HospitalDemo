package com.example.hospitalDemo.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operationdate")
    private LocalDate operationDate;

    @Column(name = "operatingroom")
    private String operatingRoom;

    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "surgeonid")
    private Surgeon surgeon;

}
