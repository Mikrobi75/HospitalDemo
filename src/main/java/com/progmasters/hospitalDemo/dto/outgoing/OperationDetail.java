package com.progmasters.hospitalDemo.dto.outgoing;

import com.progmasters.hospitalDemo.domain.Operation;

import java.time.LocalDate;

public class OperationDetail {
    private Long id;
    private LocalDate operationDate;
    private String operatingRoom;
    private Long patientId;
    private Long surgeonId;

    public OperationDetail() {
    }

    public OperationDetail(Operation operation) {
        this.id = operation.getId();
        this.operationDate = operation.getOperationDate();
        this.operatingRoom = operation.getOperatingRoom();
        try {
            this.patientId = operation.getPatient().getId();
            this.surgeonId = operation.getSurgeon().getId();
        } catch (NullPointerException e) {

        }
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(Long surgeonId) {
        this.surgeonId = surgeonId;
    }
}
