package com.pet.clinic.controller.patient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private JFXCheckBox chkIsRegistered;

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
    private JFXComboBox<String> comOwnerGender;

    @FXML
    private TextField tfOwnerPhone;

    @FXML
    private JFXTextArea taOwnerAddress;

    @FXML
    private GridPane paneOwnerPhoto;

    @FXML
    private JFXButton btnOwnerPhoto;

    @FXML
    private VBox boxOwnerForm2;

    @FXML
    private TextField tfPetName;

    @FXML
    private DateTimePicker dpPetDOB;

    @FXML
    private JFXComboBox<String> comPetKind;

    @FXML
    private VBox boxOwnerForm11;

    @FXML
    private JFXComboBox<String> comPetGender;

    @FXML
    private TextField tfPetRace;

    @FXML
    private TextField tfPetColor;

    @FXML
    private JFXButton btnSave;

    @FXML
    private GridPane panePetPhoto;

    @FXML
    private JFXButton btnPetPhoto;

    @FXML
    void initialize() {
        // photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG","*.png"));

        chkIsRegistered.setOnAction(e->{
            if(chkIsRegistered.isSelected()){
                tfOwnerFirstName.setEditable(false);
                tfOwnerLastName.setEditable(false);
                tfOwnerPhone.setEditable(false);
                comOwnerGender.setEditable(false);
                dpOwnerDOB.setEditable(false);
                taOwnerAddress.setEditable(false);
                tfOwnerFirstName.setOpacity(0.5);
                tfOwnerLastName.setOpacity(0.5);
                tfOwnerPhone.setOpacity(0.5);
                comOwnerGender.setOpacity(0.5);
                dpOwnerDOB.setOpacity(0.5);
                taOwnerAddress.setOpacity(0.5);
                tfOwnerId.setEditable(true);
                tfOwnerId.setOpacity(1);
            }
            else{
                tfOwnerFirstName.setEditable(true);
                tfOwnerLastName.setEditable(true);
                tfOwnerPhone.setEditable(true);
                comOwnerGender.setEditable(true);
                dpOwnerDOB.setEditable(true);
                taOwnerAddress.setEditable(true);
                tfOwnerFirstName.setOpacity(1);
                tfOwnerLastName.setOpacity(1);
                tfOwnerPhone.setOpacity(1);
                comOwnerGender.setOpacity(1);
                dpOwnerDOB.setOpacity(1);
                taOwnerAddress.setOpacity(1);
                tfOwnerId.setEditable(false);
                tfOwnerId.setOpacity(0.5);
            }

        });
        // set pet gender
        comPetGender.getItems().add("Jantan");
        comPetGender.getItems().add("Betina");
        // set pet kind
        comPetKind.getItems().add("Kucing");
        comPetKind.getItems().add("Anjing");
        comPetKind.getItems().add("Hamster");
        //get pet photo;
        btnPetPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                File photo = choosePhoto.showOpenDialog(new Stage());
                if(photo != null){
                 System.out.println("File Available");
                }
            }
        });


        btnSave.setOnAction(e->{

        });


    }

    private void savePatient(){
        Pet pet = new Pet();
        PetOwner petOwner = new PetOwner();
    }

/*    private int savePet(int ownerId,Timestamp timestamp){
        Pet pet = new Pet();
        pet.setOwnerId(ownerId);
        pet.setName(tfPetName.getText());
        pet.setDob(dpPetDOB.getValue());
        pet.setGender(comPetGender.getValue());
        pet.setKind(comPetKind.getValue());
        pet.setRace(tfPetRace.getText());
        pet.setColor(tfPetColor.getText());
        pet.setPhoto("keni.png");
        java.util.Date date = new java.util.Date();
        pet.setTimestamp(new Timestamp(date.getTime()));

    };*/



}
