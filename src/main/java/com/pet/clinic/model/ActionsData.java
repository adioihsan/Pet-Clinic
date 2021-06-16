package com.pet.clinic.model;

public class ActionsData {
    int medicRecordId;
    int actionId;
    String actionName;
    String description;
    double price;

    public int getMedicRecordId() {
        return medicRecordId;
    }

    public void setMedicRecordId(int medicRecordId) {
        this.medicRecordId = medicRecordId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "("+String.valueOf(this.getActionId())+") "+this.getActionName()+" | "+this.getDescription();
    }

}
