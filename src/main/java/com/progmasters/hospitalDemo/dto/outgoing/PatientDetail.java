package com.progmasters.hospitalDemo.dto.outgoing;

import com.progmasters.hospitalDemo.domain.Patient;

public class PatientDetail {
    private Long id;
    private String firstName;
    private String lastName;
    private String tajNumber;

    public PatientDetail() {
    }

    public PatientDetail(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.tajNumber = patient.getTajNumber();
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
}
