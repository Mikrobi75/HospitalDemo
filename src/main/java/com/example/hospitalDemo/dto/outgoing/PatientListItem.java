package com.example.hospitalDemo.dto.outgoing;

import com.example.hospitalDemo.domain.Patient;

public class PatientListItem {
    private String firstName;
    private String lastName;
    private String tajNumber;

    public PatientListItem() {

    }

    public PatientListItem(Patient patient) {
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.tajNumber = patient.getTajNumber();
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
