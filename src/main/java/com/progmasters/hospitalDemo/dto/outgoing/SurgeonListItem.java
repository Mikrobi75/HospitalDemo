package com.progmasters.hospitalDemo.dto.outgoing;

import com.progmasters.hospitalDemo.domain.Surgeon;

import java.time.LocalDate;

public class SurgeonListItem {
    private String firstName;
    private String lastName;
    private LocalDate beginOfPractice;

    public SurgeonListItem() {

    }

    public SurgeonListItem(Surgeon surgeon) {
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
