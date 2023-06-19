package com.progmasters.hospitalDemo.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.progmasters.hospitalDemo.domain.Patient;
import com.progmasters.hospitalDemo.domain.Surgeon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class SurgeonCommand {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate beginOfPractice;

    public SurgeonCommand(Surgeon surgeon) {
        this.firstName = surgeon.getFirstName();
        this.lastName = surgeon.getLastName();
        this.beginOfPractice = surgeon.getBeginOfPractice();
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
}
