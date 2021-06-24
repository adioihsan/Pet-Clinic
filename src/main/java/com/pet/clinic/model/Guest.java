package com.pet.clinic.model;

import java.time.LocalDateTime;

public class Guest {
    private int petId;
    private int petOwnerId;
    private String petOwnerName;
    private String petName;
    LocalDateTime visitTime;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(int petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getPetOwnerName() {
        return petOwnerName;
    }

    public void setPetOwnerName(String petOwnerName) {
        this.petOwnerName = petOwnerName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }
}
