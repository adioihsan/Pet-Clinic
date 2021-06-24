package com.pet.clinic.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pet.clinic.controller.login.ValidateUser;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.User;
import com.pet.clinic.model.dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserSettingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfUserFirstName;

    @FXML
    private TextField tfUserLastName;

    @FXML
    private TextField tfUserPhoneNumber;

    @FXML
    private TextField tfUserUsername;

    @FXML
    private PasswordField pfUserPassword;

    @FXML
    private PasswordField pfUserPasswordRe;

    @FXML
    private Label lblCheckUsername;

    @FXML
    private Label lblCheckPassword;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private ImageView imgAddPhoto;

    @FXML
    private JFXButton btnPhoto;

    @FXML
    private JFXCheckBox chkPatient;

    @FXML
    private JFXCheckBox chkMedicRecord;

    @FXML
    private JFXCheckBox chkPayment;

    @FXML
    private JFXCheckBox chkReport;

    @FXML
    private JFXCheckBox chkGuestBook;

    @FXML
    private JFXCheckBox chkMedicine;

    @FXML
    private JFXCheckBox chkVet;

    @FXML
    private JFXCheckBox chkAdmin;

    @FXML
    private HBox boxBtns;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TextField tfUserAdminType;

    private File photo = null;

    @FXML
    void initialize() {
        //field rules
        tfUserPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfUserPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //check password on type
        pfUserPasswordRe.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                checkInputPassword();
            }
        });
        //check username
       tfUserUsername.setOnKeyTyped(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               checkUsername();
           }
       });

       //user photo
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png") ,
                new FileChooser.ExtensionFilter("JPG","*.JPG"),
                new FileChooser.ExtensionFilter("JPEG","*.JPEG"));

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
        //end of user photo

        //btn save
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(saveUser()){
                    Message.showSuccess();
                }
                else{
                    Message.showFailed();
                }
            }
        });

    }


    private void checkInputPassword(){
        if(!pfUserPassword.getText().equals(pfUserPasswordRe.getText())){
            lblCheckPassword.setText("Password Tidak Sama");
            lblCheckPassword.setVisible(true);
            pfUserPasswordRe.setStyle("fx-text-fill:red;");
        }
        else{
            lblCheckPassword.setVisible(false);
            pfUserPasswordRe.setStyle("fx-text-fill:black;");
        }
    }
    private void checkUsername(){
        if(UserDao.checkIsRegistered(tfUserUsername.getText())){
            lblCheckUsername.setText("Username sudah terdaftar");
            lblCheckUsername.setVisible(true);
        }
        else{
            lblCheckUsername.setVisible(false);
        }
    }
    private String getPrivilege(){
        String privilege ="";
        if(chkPatient.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkMedicRecord.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkPayment.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkReport.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkGuestBook.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkMedicine.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkVet.isSelected()) privilege += '1';
        else privilege +='0';
        if(chkAdmin.isSelected()) privilege += '1';
        else privilege +='0';
    return privilege;
    }

    private boolean saveUser(){
        User user = new User();
        user.setFirstName(tfUserFirstName.getText());
        user.setLastName(tfUserLastName.getText());
        user.setPhoneNumber(Double.parseDouble(tfUserPhoneNumber.getText()));
        user.setUsername(tfUserUsername.getText());
        user.setSalt(ValidateUser.getSalt().toString());
        user.setType(tfUserAdminType.getText());
        user.setHash(ValidateUser.generatePassword(pfUserPassword.getText(),
                user.getSalt().getBytes(StandardCharsets.UTF_8)));
        user.setPrivilege(getPrivilege());
        int userId = UserDao.insertUser(user);
        String fileName = saveLocalUserPhotos(userId,user.getFirstName());
        UserDao.updateUserPhoto(userId,fileName);
        return  userId != 0;
    }
    private String saveLocalUserPhotos(int id,String name){
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
