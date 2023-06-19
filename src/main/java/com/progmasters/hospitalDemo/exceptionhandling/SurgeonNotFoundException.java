package com.progmasters.hospitalDemo.exceptionhandling;

public class SurgeonNotFoundException extends RuntimeException{
    private final long SurgeonId;

    public SurgeonNotFoundException(long SurgeonId) {
        this.SurgeonId = SurgeonId;
    }

    public long getSurgeonId() {
        return SurgeonId;
    }

}
