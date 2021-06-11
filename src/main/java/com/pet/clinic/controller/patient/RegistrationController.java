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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pet.clinic.helper.Message;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetKind;
import com.pet.clinic.model.dao.PetDao;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.dao.PetOwnerDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
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
    private TextArea taOwnerAddress;

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

    @FXML
    private JFXButton btnSearch;

    @FXML
    private Label lblSearchStatus;

    @FXML
    private Label lblOwnerPhotoEmpty;

    @FXML
    private Label lblPetPhotoEmpty;

    //
    private File petPhoto;
    private File ownerPhoto;

    @FXML
    void initialize() {

        // check is user registered
        setEditableForm(true,1);
        chkIsRegistered.setOnAction(e->{
            if(chkIsRegistered.isSelected()){
                setEditableForm(false,0.5);
            }
            else{
                setEditableForm(true,1);
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
        setPetKinds();


        //set dob
        dpOwnerDOB.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(dpOwnerDOB.getValue() == null)
                dpOwnerDOB.setValue(LocalDate.of(2000,01,01));
            }
        });

        dpPetDOB.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(dpPetDOB.getValue() == null)
                    dpPetDOB.setValue(LocalDate.of(2018,01,01));
            }
        });


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
                    lblOwnerPhotoEmpty.setVisible(false);
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
                    btnPetPhoto.setText("Ganti Photo");
                    lblPetPhotoEmpty.setVisible(false);
                }
            }
        });

        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int ownerId = 0;
                if(!tfOwnerId.getText().equalsIgnoreCase("")){
                    ownerId = Integer.valueOf(tfOwnerId.getText());
                    boolean status = setOwner(ownerId);
                    if(!status){
                        lblSearchStatus.setText("Pemilik Tidak di Temukan");
                        lblSearchStatus.setVisible(true);
                    }
                    else {
                        lblSearchStatus.setVisible(false);
                        btnOwnerPhoto.setDisable(true);
                    }
                }
            }
        });
        tfOwnerId.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int ownerId = 0;
                if(!tfOwnerId.getText().equalsIgnoreCase("")){
                    ownerId = Integer.valueOf(tfOwnerId.getText());
                    boolean status = setOwner(ownerId);
                    if(!status){
                        lblSearchStatus.setText("Pemilik Tidak di Temukan");
                        lblSearchStatus.setVisible(true);
                        clearOwnerForm();
                    }
                    else {
                        lblSearchStatus.setVisible(false);
                        btnOwnerPhoto.setDisable(true);
                    }
                }
            }
        });

        tfOwnerId.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tfOwnerId.setText("");
                clearOwnerForm();
                setOwnerPhoto(null);
            }
        });


        btnSave.setOnAction(e-> {
            if (isFormNotNull()){
                boolean status = savePatient();
                if (status) {
                    clearPetForm();
                    clearOwnerForm();
                    Message.showSuccess(" Registrasi Berhasil.");
                }
                else {
                    Message.showFailed("Terjadi Kesalahan ! . Registrasi Gagal.");
                }
            }
        });

        //END OF INITIALIZE
    }

    //set Pet kinds
    private void setPetKinds(){
        ArrayList<PetKind> petKinds = PetDao.getPetKind();
        Iterator<PetKind> itr = petKinds.iterator();
        while (itr.hasNext()){
            comPetKind.getItems().add(itr.next().getName());
        }
    }


    // Turn on/off form based on registration status
    private void setEditableForm(boolean isEditable , double opacity){
        tfOwnerFirstName.setEditable(isEditable);
        tfOwnerLastName.setEditable(isEditable);
        tfOwnerPhone.setEditable(isEditable);
        comOwnerGender.setDisable(!isEditable);
        dpOwnerDOB.setDisable(!isEditable);
        taOwnerAddress.setEditable(isEditable);
        tfOwnerFirstName.setOpacity(opacity);
        tfOwnerLastName.setOpacity(opacity);
        dpOwnerDOB.setOpacity(opacity);
        taOwnerAddress.setOpacity(opacity);
        tfOwnerId.setEditable(!isEditable);
        btnSearch.setDisable(isEditable);
        btnOwnerPhoto.setDisable(!isEditable);
        tfOwnerPhone.setOpacity(opacity);
        tfOwnerId.setOpacity(1);
        tfOwnerId.setMouseTransparent(false);
        if(isEditable) {
            tfOwnerId.setOpacity(0.5);
            tfOwnerId.setMouseTransparent(true);
        }
    }
    private  void clearOwnerForm(){
        tfOwnerFirstName.clear();
        tfOwnerLastName.clear();
        tfOwnerPhone.clear();
        comOwnerGender.setPromptText("Jenis Kelamin");
        dpOwnerDOB.setPromptText("Tanggal Lahir");
        taOwnerAddress.clear();
        dpOwnerDOB.setValue(null);
        comOwnerGender.setValue(null);
        setOwnerPhoto(null);
    };

    private void clearPetForm(){
        tfPetColor.clear();
        tfPetRace.clear();
        tfPetName.clear();
        dpPetDOB.setValue(null);
        comPetKind.setValue(null);
        comPetGender.setValue(null);
        imgPet.setImage(null);
    }


    //Save patient
    private boolean savePatient(){
        boolean status = false;
        Timestamp timestamp = Timestamp.from(Instant.now());
        if(chkIsRegistered.isSelected()){
            int ownerId = Integer.valueOf(tfOwnerId.getText());
            int petId = savePet(ownerId,timestamp);
            status = petId != 0;
        }
        else {
            int ownerId = savePetOwner(timestamp);
            int petId = savePet(ownerId, timestamp);
            status =  ownerId != 0 && petId != 0;
        }
        return status;
    }

    private int savePet(int ownerId, Timestamp timestamp){
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
        id = PetDao.insertPet(pet);
        String photoName = saveLocalPetPhotos(id,pet.getName());
        PetDao.insertPetPhoto(id,photoName);
        return id;
    };

    private int savePetOwner(Timestamp timestamp){
        int id = 0;
        PetOwner petOwner = new PetOwner();
        petOwner.setFirstName(tfOwnerFirstName.getText());
        petOwner.setLastName(tfOwnerLastName.getText());
        petOwner.setDob(dpOwnerDOB.getValue());
        petOwner.setGender(comOwnerGender.getValue());
        petOwner.setPhoneNumber(Double.valueOf(tfOwnerPhone.getText()));
        petOwner.setAddress(taOwnerAddress.getText());
        petOwner.setTimestamp(timestamp);
        id = PetOwnerDao.insertPetOwner(petOwner);
        String photoName = saveLocalOwnerPhotos(id,petOwner.getFirstName());
        PetOwnerDao.insertPetOwnerPhoto(id,photoName);
        return id;
    }

    private String saveLocalOwnerPhotos(int ownerId,String name){
        String extension = getFileExtension(ownerPhoto.getName()).get();
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

    private boolean setOwner(int id){
        PetOwner petOwner = PetOwnerDao.getOwner(id);
        if(petOwner != null){
            tfOwnerFirstName.setText(petOwner.getFirstName());
            tfOwnerLastName.setText(petOwner.getLastName());
            dpOwnerDOB.setValue(petOwner.getDob());
            comOwnerGender.setValue(petOwner.getGender());
            tfOwnerPhone.setText(String.valueOf(petOwner.getPhoneNumber()));
            taOwnerAddress.setText(petOwner.getAddress());
            setOwnerPhoto(petOwner.getPhoto());
            return true;
        }
        else{
            return false;
        }
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private void setOwnerPhoto(String photoName){
        File photo = new File("files/photos/petOwner/"+photoName);
        if(photo != null){
            imgOwner.setImage(new Image(photo.toURI().toString()));
        }
    }

    // Check if columns null

    private boolean isFormNotNull(){
        boolean status = true;
        // owner form
        if(tfOwnerFirstName.getText().equalsIgnoreCase("")){
            tfOwnerFirstName.setPromptText(("Nama depan tidak boleh kosong ! "));
            tfOwnerFirstName.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(tfOwnerLastName.getText().equalsIgnoreCase("")){
            tfOwnerLastName.setPromptText(("Nama Belakang tidak boleh kosong ! "));
            tfOwnerLastName.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(dpOwnerDOB.getValue() == null){
            dpOwnerDOB.setPromptText(("Tanggal lahir tidak boleh kosong ! "));
            dpOwnerDOB.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px");
            status = false;
        }
        if(comOwnerGender.getValue() == null){
            comOwnerGender.setPromptText(("Jenis Kelamin tidak boleh kosong ! "));
            comOwnerGender.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px;");
            status = false;
        }
        if(tfOwnerPhone.getText().equalsIgnoreCase("")){
            tfOwnerPhone.setPromptText(("Nomer telpon tidak boleh kosong ! "));
            tfOwnerPhone.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(taOwnerAddress.getText().equalsIgnoreCase("")){
            taOwnerAddress.setPromptText(("Alamat tidak boleh kosong ! "));
            taOwnerAddress.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(ownerPhoto == null){
            lblOwnerPhotoEmpty.setVisible(true);
            status = false;
            if(!tfOwnerId.getText().equalsIgnoreCase("")){
                lblOwnerPhotoEmpty.setVisible(false);
                status = true;
            }
        }

        //pet form
        if(tfPetName.getText().equalsIgnoreCase("")){
            tfPetName.setPromptText(("Nama Peliharaan tidak boleh kosong ! "));
            tfPetName.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(dpPetDOB.getValue() == null){
            dpPetDOB.setPromptText(("Tanggal lahir tidak boleh kosong ! "));
            dpPetDOB.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px");
            status = false;
        }
        if(comPetKind.getValue() == null){
            comPetKind.setPromptText(("Jenis Peliharaan tidak boleh kosong ! "));
            comPetKind.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px;");
            status = false;
        }
        if(comPetGender.getValue() == null){
            comPetGender.setPromptText(("Jenis Kelamin tidak boleh kosong ! "));
            comPetGender.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px;");
            status = false;
        }
        if(tfPetRace.getText().equalsIgnoreCase("")){
            tfPetRace.setPromptText(("Ras Peliharaan tidak boleh kosong! "));
            tfPetRace.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(tfPetColor.getText().equalsIgnoreCase("")){
            tfPetColor.setPromptText(("Warna Bulu atau Kulit tidak boleh kosong! "));
            tfPetColor.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(petPhoto == null){
            lblPetPhotoEmpty.setVisible(true);
            status = false;
        }

        return status;
    }

}
