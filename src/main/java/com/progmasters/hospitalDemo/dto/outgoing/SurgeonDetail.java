package com.progmasters.hospitalDemo.dto.outgoing;

import com.progmasters.hospitalDemo.domain.Surgeon;

import java.time.LocalDate;

public class SurgeonDetail {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate beginOfPractice;

    public SurgeonDetail() {

    }

    public SurgeonDetail(Surgeon surgeon) {
        this.id = surgeon.getId();
        this.firstName = surgeon.getFirstName();
        this.lastName = surgeon.getLastName();
        this.beginOfPractice = surgeon.getBeginOfPractice();
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

    public LocalDate getBeginOfPractice() {
        return beginOfPractice;
    }

    public void setBeginOfPractice(LocalDate beginOfPractice) {
        this.beginOfPractice = beginOfPractice;
    }
}
