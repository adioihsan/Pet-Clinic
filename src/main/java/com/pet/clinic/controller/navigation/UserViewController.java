package com.pet.clinic.controller.navigation;

import com.jfoenix.controls.JFXButton;

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

public class UserViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField pfCpOldPassword;

    @FXML
    private Label lblCheckOldPassword;

    @FXML
    private PasswordField pfCpNewPassword;

    @FXML
    private PasswordField pfCpNewPasswordRe;

    @FXML
    private Label lblCheckNewPassword;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    private TextField tfEditUserFirstName;

    @FXML
    private TextField tfEditUserLastName;

    @FXML
    private TextField tfEditUserPhoneNumber;

    @FXML
    private TextField tfEditUserAdminType;

    @FXML
    private Label lblEditCheckPassword;

    @FXML
    private GridPane panePetPhoto1;

    @FXML
    private ImageView imgPhoto1;

    @FXML
    private ImageView imgEditPhoto;

    @FXML
    private JFXButton btnEditPhoto;

    @FXML
    private HBox boxBtns1;

    @FXML
    private JFXButton btnEditSave;

    @FXML
    private TextField tfCpUsername;

    private File photo;

    private User user = ValidateUser.getLoggedUser();

    @FXML
    void initialize() {
        //load data
        loadUserFields(user);
        loadUserPhoto(user);

        //field rules
        tfEditUserPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfEditUserPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        //check password on type
        pfCpNewPasswordRe.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                checkInputNewPassword();
            }
        });

        // yeay the buttons
        btnChangePassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(checkInputNewPassword() && !isCpFieldNull() && checkOldPassword()){
                    if(saveNewPassword()){
                        Message.showSuccess("Password berhasil di ganti");
                        cleanCpField();
                    }
                    else
                        Message.showFailed("Terjadi kesalahan, Password gagal di ganti !");
                }
            }
        });
        //edit user data
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        btnEditPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                photo = choosePhoto.showOpenDialog(new Stage());
                if (photo != null) {
                    imgPhoto1.setImage(new Image(photo.toURI().toString()));
                    imgEditPhoto.setOpacity(0);
                    btnEditPhoto.setText("Ganti Photo");
                }
            }
        });
        btnEditSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isEditFieldNull()){
                    if(saveEditedUser()){
                        Message.showSuccess();
                    }
                    else{
                        Message.showFailed();
                    }
                }
            }
        });


    }
    //load user
    private void loadUserFields(User user){
        tfEditUserFirstName.setText(user.getFirstName());
        tfEditUserLastName.setText(user.getLastName());
        tfEditUserAdminType.setText(user.getType());
        tfEditUserPhoneNumber.setText(String.format("%.0f",user.getPhoneNumber()));
        tfCpUsername.setText(user.getUsername());

    }

    private void loadUserPhoto(User user){
        File photo = new File("files/photos/admin/"+user.getPhoto());
        if(photo.exists()){
            imgPhoto1.setImage(new Image(photo.toURI().toString()));
            imgEditPhoto.setOpacity(0);
        }
        else imgPhoto1.setImage(null);
    }

    private boolean checkInputNewPassword(){
        if (!pfCpNewPassword.getText().equals(pfCpNewPasswordRe.getText())) {
            lblCheckNewPassword.setText("Password Tidak Sama");
            lblCheckNewPassword.setVisible(true);
            pfCpNewPasswordRe.setStyle("fx-text-fill:red;");
            return false;
        } else {
            lblCheckNewPassword.setVisible(false);
            pfCpNewPasswordRe.setStyle("fx-text-fill:silver;");
            return true;
        }
    }

    private boolean checkOldPassword(){
        if(!ValidateUser.isValid(tfCpUsername.getText(),pfCpOldPassword.getText())){
            lblCheckOldPassword.setText("Password Lama Salah !");
            lblCheckOldPassword.setVisible(true);
            return false;
        }
        else{
            lblCheckOldPassword.setVisible(false);
            return true;
        }
    };

    private boolean isCpFieldNull(){
        boolean status = false;
        if(tfCpUsername.getText().equalsIgnoreCase("")){
            tfCpUsername.setPromptText("Klik User pada tabel di samping ! ");
            tfCpUsername.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(pfCpOldPassword.getText().equalsIgnoreCase("")){
            pfCpOldPassword.setPromptText("Masukan password lama ! ");
            pfCpOldPassword.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(pfCpNewPassword.getText().equalsIgnoreCase("")){
            pfCpNewPassword.setPromptText("Masukan Password Baru ! ");
            pfCpNewPassword.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(pfCpNewPasswordRe.getText().equalsIgnoreCase("")){
            pfCpNewPasswordRe.setPromptText("Ulangi Password Baru ! ");
            pfCpNewPasswordRe.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(!status){
            tfCpUsername.setPromptText("Username");
            tfCpUsername.setStyle("-fx-prompt-text-fill:black");
            pfCpOldPassword.setPromptText("Password Lama ");
            pfCpOldPassword.setStyle("-fx-prompt-text-fill:silver");
            pfCpNewPassword.setPromptText("Password Baru");
            pfCpNewPassword.setStyle("-fx-prompt-text-fill:silver");
            pfCpNewPasswordRe.setPromptText("Ulangi Password Baru ");
            pfCpNewPasswordRe.setStyle("-fx-prompt-text-fill:silver");
        }
        return status;
    }

    private boolean saveNewPassword(){
        String salt = ValidateUser.getSalt().toString();
        String hash = ValidateUser.generatePassword(pfCpNewPassword.getText(),salt.getBytes(StandardCharsets.UTF_8));
        String username = tfCpUsername.getText();
        return UserDao.updatePassword(username,hash,salt.toString());
    }

    // Edit user data
    private boolean saveEditedUser() { ;
        user.setFirstName(tfEditUserFirstName.getText());
        user.setLastName(tfEditUserLastName.getText());
        user.setPhoneNumber(Double.parseDouble(tfEditUserPhoneNumber.getText()));
        user.setType(tfEditUserAdminType.getText());
        if(photo != null) {
            String fileName = saveLocalUserPhotos(user.getId(), user.getUsername());
            UserDao.updateUserPhoto(user.getId(), fileName);
        }
        return UserDao.updateUser(user);
    }

    private String saveLocalUserPhotos(int id, String name) {
        String extension = getFileExtension(photo.getName()).get();
        String fileName = String.valueOf(id) + name + "." + extension;
        Path copied = Paths.get("files/photos/admin/" + fileName);
        Path source = photo.toPath();
        try {
            Files.copy(source, copied, StandardCopyOption.REPLACE_EXISTING);
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

    private void clearEditForm(){
        tfEditUserFirstName.setText("");
        tfEditUserLastName.setText("");
        tfEditUserPhoneNumber.setText("");
        tfEditUserAdminType.setText("");
        photo = null;
        imgPhoto1.setImage(null);
    }

    private boolean isEditFieldNull(){
        boolean status = false;
        if(tfEditUserFirstName.getText().equalsIgnoreCase("")){
            tfEditUserFirstName.setPromptText("Nama Depan tidak boleh kosong");
            tfEditUserFirstName.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfEditUserLastName.getText().equalsIgnoreCase("")){
            tfEditUserLastName.setPromptText("Nama Belakang tidak boleh kosong");
            tfEditUserLastName.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfEditUserPhoneNumber.getText().equalsIgnoreCase("")){
            tfEditUserPhoneNumber.setPromptText("Nomer Telpon tidak boleh kosong");
            tfEditUserPhoneNumber.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfEditUserAdminType.getText().equalsIgnoreCase("")){
            tfEditUserAdminType.setPromptText("Tipe Admin tidak boleh kosong");
            tfEditUserAdminType.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(!status){
            tfEditUserFirstName.setPromptText("Nama Depan");
            tfEditUserFirstName.setStyle("-fx-prompt-text-fill:silver");
            tfEditUserLastName.setPromptText("Nama Belakang");
            tfEditUserLastName.setStyle("-fx-prompt-text-fill:silver");
            tfEditUserPhoneNumber.setPromptText("Nomer Telpon");
            tfEditUserPhoneNumber.setStyle("-fx-prompt-text-fill:silver");
            tfEditUserAdminType.setPromptText("Tipe Admin");
            tfEditUserAdminType.setStyle("-fx-prompt-text-fill:silver");
        }
        return status;
    }

    private void cleanCpField(){
        pfCpNewPasswordRe.setText("");
        pfCpNewPassword.setText("");
        pfCpOldPassword.setText("");
    }
}
