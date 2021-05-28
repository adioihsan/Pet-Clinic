package com.pet.clinic.controller.patient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tornadofx.control.DateTimePicker;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private VBox boxOwnerForm;

    @FXML
    private TextField tfOwnerId;

    @FXML
    private TextField tfOwnerFirstName;

    @FXML
    private TextField tfOwnerLastName;

    @FXML
    private DateTimePicker dpOwnerDOB;

    @FXML
    private VBox boxOwnerForm1;

    @FXML
    private JFXComboBox<?> comOwnerGender;

    @FXML
    private TextField tfOwnerPhone;

    @FXML
    private JFXTextArea taOwnerAddress;

    @FXML
    private JFXButton btnOwnerPhoto;

    @FXML
    void initialize() {
        assert boxHeader != null : "fx:id=\"boxHeader\" was not injected: check your FXML file 'registration.fxml'.";
        assert boxOwnerForm != null : "fx:id=\"boxOwnerForm\" was not injected: check your FXML file 'registration.fxml'.";
        assert tfOwnerId != null : "fx:id=\"tfOwnerId\" was not injected: check your FXML file 'registration.fxml'.";
        assert tfOwnerFirstName != null : "fx:id=\"tfOwnerFirstName\" was not injected: check your FXML file 'registration.fxml'.";
        assert tfOwnerLastName != null : "fx:id=\"tfOwnerLastName\" was not injected: check your FXML file 'registration.fxml'.";
        assert dpOwnerDOB != null : "fx:id=\"dpOwnerDOB\" was not injected: check your FXML file 'registration.fxml'.";
        assert boxOwnerForm1 != null : "fx:id=\"boxOwnerForm1\" was not injected: check your FXML file 'registration.fxml'.";
        assert comOwnerGender != null : "fx:id=\"comOwnerGender\" was not injected: check your FXML file 'registration.fxml'.";
        assert tfOwnerPhone != null : "fx:id=\"tfOwnerPhone\" was not injected: check your FXML file 'registration.fxml'.";
        assert taOwnerAddress != null : "fx:id=\"taOwnerAddress\" was not injected: check your FXML file 'registration.fxml'.";
        assert btnOwnerPhoto != null : "fx:id=\"btnOwnerPhoto\" was not injected: check your FXML file 'registration.fxml'.";

    }
}
