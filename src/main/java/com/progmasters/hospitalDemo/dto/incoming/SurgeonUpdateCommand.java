package com.progmasters.hospitalDemo.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SurgeonUpdateCommand {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate beginOfPractice;

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
}
