package com.pet.clinic.model;

public class Prescription {
    int medicRecordId;
    int medicineId;
    String medicineName;
    String description;
    String unit;
    int amount;
    double price;
    double priceTotal;


    public int getMedicRecordId() {
        return medicRecordId;
    }

    public void setMedicRecordId(int medicRecordId) {
        this.medicRecordId = medicRecordId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceTotal() {
        return this.getPrice()*this.getAmount();
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Override
    public String toString(){
        return "("+String.valueOf(this.getMedicineId())+") "+this.getMedicineName()+" | "+
                "X"+this.getAmount()+" "+this.getUnit()+" | "+this.description;
    }

}
