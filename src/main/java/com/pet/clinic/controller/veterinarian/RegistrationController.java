package com.pet.clinic.controller.veterinarian;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pet.clinic.model.Veterinarian;
import com.pet.clinic.model.dao.VeterinarianDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfSpecialist;

    @FXML
    private DateTimePicker dpDob;

    @FXML
    private JFXComboBox<String> comGender;

    @FXML
    private TextField tfPhone;

    @FXML
    private JFXTextArea taAddress;

    @FXML
    private GridPane paneOwnerPhoto;

    @FXML
    private ImageView imgAddPhoto;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private JFXButton btnPhoto;

    @FXML
    private JFXButton btnSave;

    File photo;

    @FXML
    void initialize() {

        //set gender
        comGender.getItems().add("Laki-Laki");
        comGender.getItems().add("Perempuan");


        // set Phone Number rules
        tfPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png") ,
                new FileChooser.ExtensionFilter("JPG","*.JPG"),
                new FileChooser.ExtensionFilter("JPEG","*.JPEG"));
        // pet Owner Photo Chooser
        btnPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                photo = choosePhoto.showOpenDialog(new Stage());
                if(photo != null){
                    imgPhoto.setImage(new Image(photo.toURI().toString()));
                    imgAddPhoto.setOpacity(0);
                    btnPhoto.setText("Ganti Photo");
                }
            }
        });
        
        btnSave.setOnAction(e -> {
            int veterinarianId = saveVeterinarian();
            if(veterinarianId > 0 ){
                Alert success =  new Alert(Alert.AlertType.INFORMATION,"Dokter Berhasil Ditambahkan");
                success.show();
            }
            else
            {
                Alert failed = new Alert(Alert.AlertType.ERROR,"Terjadi Kesalahan" +
                        "Dokter Gagal Ditambahkan");
                failed.show();
            }
        });
    }
    
    public int saveVeterinarian(){
        int id = 0;
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName(tfFirstName.getText());
        veterinarian.setLastName(tfLastName.getText());
        veterinarian.setTitle(tfTitle.getText());
        veterinarian.setSpecialist(tfSpecialist.getText());
        veterinarian.setDob(dpDob.getValue());
        veterinarian.setGender(comGender.getValue());
        veterinarian.setPhoneNumber(Double.valueOf(tfPhone.getText()));
        veterinarian.setAddress(taAddress.getText());

        id = VeterinarianDao.insertVeterinarian(veterinarian);
        if(id != 0) {
            String photoName = saveLocalOwnerPhotos(id, veterinarian.getFirstName());
            VeterinarianDao.insertPhoto(id, photoName);
        }
        return id;
    }

    private String saveLocalOwnerPhotos(int id,String name){
        String extension = getFileExtension(photo.getName()).get();
        String fileName = String.valueOf(id)+name+"."+extension;
        Path copied = Paths.get("files/photos/veterinarian/"+fileName);
        Path source = photo.toPath();
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
