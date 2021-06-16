package com.pet.clinic.controller.medicRecord;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ActionPopupController {

    @FXML
    private Label lblAction;

    @FXML
    private TextArea taDescription;

    @FXML
    private JFXButton btnOK;

    @FXML
    void initialize() {

    }

    public void setActionName(String name){
        lblAction.setText(name);
    }

    public String getDescription(){
        return taDescription.getText();
    }

    public JFXButton getBtnOk(){
        return btnOK;
    }

}
