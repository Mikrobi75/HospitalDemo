package com.progmasters.hospitalDemo.dto.incoming;

import com.progmasters.hospitalDemo.domain.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class OperationCommand {
    @NotNull
    private LocalDate operationDate;
    @NotBlank
    private String operatingRoom;
    @NotNull
    private Long patientId;
    @NotNull
    private Long surgeonId;

    public OperationCommand(Operation operation) {
        this.operatingRoom = operation.getOperatingRoom();
        this.operationDate = operation.getOperationDate();
        this.patientId = operation.getPatient().getId();
        this.surgeonId = operation.getSurgeon().getId();
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
