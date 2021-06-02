package com.pet.clinic.controller.patient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pet.clinic.model.Pet;
import com.pet.clinic.model.dao.PetDao;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.dao.PetOwnerDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    private ImageView imgPet;
    @FXML
    private ImageView imgOwner;

    @FXML
    private ImageView imgAddOwnerPhoto;
    @FXML
    private ImageView imgAddPetPhoto;

    //
    private File petPhoto;
    private File ownerPhoto;

    @FXML
    void initialize() {

        // check is user registered
        turnOnForm();
        chkIsRegistered.setOnAction(e->{
            if(chkIsRegistered.isSelected()){
                turnOffForm();
            }
            else{
                turnOnForm();
                clearOwnerForm();
            }

        });
        // set Id rules
        tfOwnerId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfOwnerId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // set Phone Number rules
        tfOwnerPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfOwnerPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //set petOwner gender
        comOwnerGender.getItems().add("Laki-Laki");
        comOwnerGender.getItems().add("Perempuan");

        // set pet gender
        comPetGender.getItems().add("Jantan");
        comPetGender.getItems().add("Betina");

        // set pet kind
        comPetKind.getItems().add("Kucing");
        comPetKind.getItems().add("Anjing");
        comPetKind.getItems().add("Hamster");

        // photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png"));
        // pet Owner Photo Chooser
        btnOwnerPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ownerPhoto = choosePhoto.showOpenDialog(new Stage());
                if(ownerPhoto != null){
                    imgOwner.setImage(new Image(ownerPhoto.toURI().toString()));
                    imgAddOwnerPhoto.setOpacity(0);
                    btnOwnerPhoto.setText("Ganti Photo");
                }
            }
        });
        // Pet Photo Chooser
        btnPetPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                petPhoto = choosePhoto.showOpenDialog(new Stage());
                if(petPhoto != null) {
                    imgPet.setImage(new Image(petPhoto.toURI().toString()));
                    imgAddPetPhoto.setOpacity(0);
                    btnOwnerPhoto.setText("Ganti Photo");
                }
            }
        });

        btnSave.setOnAction(e->{
            savePatient();
        });

    }

    // Turn on/off form based on registration status
    private void turnOffForm(){
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
    private void turnOnForm(){
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
    private  void clearOwnerForm(){
        tfOwnerFirstName.clear();
        tfOwnerLastName.clear();
        tfOwnerPhone.clear();
        comOwnerGender.setPromptText("Jenis Kelamin");
        dpOwnerDOB.setPromptText("Tanggal Lahir");
        taOwnerAddress.clear();
    }

    //Save patient
    private boolean savePatient(){
        Timestamp timestamp = Timestamp.from(Instant.now());
        if(chkIsRegistered.isSelected()){
            int ownerId = Integer.valueOf(tfOwnerId.getText());
            int petId = setPet(ownerId,timestamp);
            return petId != 0;
        }
        else {
            int ownerId = setPetOwner(timestamp);
            int petId = setPet(ownerId, timestamp);
            return ownerId != 0 && petId != 0;
        }
    }

    private int setPet(int ownerId,Timestamp timestamp){
        int id = 0;
        Pet pet = new Pet();
        pet.setOwnerId(ownerId);
        pet.setName(tfPetName.getText());
        pet.setDob(dpPetDOB.getValue());
        pet.setGender(comPetGender.getValue());
        pet.setKind(comPetKind.getValue());
        pet.setRace(tfPetRace.getText());
        pet.setColor(tfPetColor.getText());
        pet.setTimestamp(timestamp);
        id = PetDao.savePet(pet);
        String photoName = saveLocalPetPhotos(id,pet.getName());
        PetDao.savePetPhoto(id,photoName);
        return id;
    };

    private int setPetOwner(Timestamp timestamp){
        int id = 0;
        PetOwner petOwner = new PetOwner();
        petOwner.setFirstName(tfOwnerFirstName.getText());
        petOwner.setLastName(tfOwnerLastName.getText());
        petOwner.setDob(dpOwnerDOB.getValue());
        petOwner.setGender(comOwnerGender.getValue());
        petOwner.setPhoneNumber(Integer.valueOf(tfOwnerPhone.getText()));
        petOwner.setAddress(taOwnerAddress.getText());
        petOwner.setTimestamp(timestamp);
        id = PetOwnerDao.savePetOwner(petOwner);
        String photoName = saveLocalOwnerPhotos(id,petOwner.getFirstName());
        PetOwnerDao.savePetOwnerPhoto(id,photoName);
        return id;
    }

    private String saveLocalOwnerPhotos(int ownerId,String name){
        String extension = getFileExtension(petPhoto.getName()).get();
        String fileName = String.valueOf(ownerId)+name+"."+extension;
        Path copied = Paths.get("files/photos/petOwner/"+fileName);
        Path source = ownerPhoto.toPath();
        try {
            Files.copy(source,copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    private String saveLocalPetPhotos(int petId,String name){
        String extension = getFileExtension(petPhoto.getName()).get();
        String fileName = String.valueOf(petId)+name+"."+extension;
        Path copied = Paths.get("files/photos/pet/"+fileName);
        Path source = petPhoto.toPath();
        try {
            Files.copy(source,copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
