package com.example.hospitalDemo.domain;

import com.example.hospitalDemo.dto.incoming.OperationCommand;
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
    @JoinColumn(name = "patientid", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "surgeonid", referencedColumnName = "id")
    private Surgeon surgeon;

    public Operation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperatingRoom() {
        return operatingRoom;
    }

    public void setOperatingRoom(String operatingRoom) {
        this.operatingRoom = operatingRoom;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Surgeon getSurgeon() {
        return surgeon;
    }

    public void setSurgeon(Surgeon surgeon) {
        this.surgeon = surgeon;
    }
}
