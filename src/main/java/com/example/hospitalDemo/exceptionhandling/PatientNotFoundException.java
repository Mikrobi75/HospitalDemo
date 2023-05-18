package com.example.hospitalDemo.exceptionhandling;

public class PatientNotFoundException extends RuntimeException{
    private final long PatientId;

    public PatientNotFoundException(long PatientId) {
        this.PatientId = PatientId;
    }

    public long getPatientId() {
        return PatientId;
    }
}
