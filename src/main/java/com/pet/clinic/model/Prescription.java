package com.pet.clinic.model;

public class Prescription {
    int medicRecordId;
    int veterinarianId;
    int medicineId;
    String medicineName;
    String description;
    int amount;

    public int getMedicRecordId() {
        return medicRecordId;
    }

    public void setMedicRecordId(int medicRecordId) {
        this.medicRecordId = medicRecordId;
    }

    public int getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(int veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
