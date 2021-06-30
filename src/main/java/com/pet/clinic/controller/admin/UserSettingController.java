package com.pet.clinic.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import com.pet.clinic.controller.login.ValidateUser;
import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.User;
import com.pet.clinic.model.dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserSettingController {
    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfUserFirstName;

    @FXML
    private TextField tfUserLastName;

    @FXML
    private TextField tfUserPhoneNumber;

    @FXML
    private TextField tfUserAdminType;

    @FXML
    private TextField tfUserUsername;

    @FXML
    private Label lblCheckUsername;

    @FXML
    private PasswordField pfUserPassword;

    @FXML
    private PasswordField pfUserPasswordRe;

    @FXML
    private Label lblCheckPassword;

    @FXML
    private GridPane panePetPhoto;

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
    private JFXCheckBox chkGuestBook;

    @FXML
    private JFXCheckBox chkMedicine;

    @FXML
    private JFXCheckBox chkPayment;

    @FXML
    private JFXCheckBox chkReport;

    @FXML
    private JFXCheckBox chkVet;

    @FXML
    private JFXCheckBox chkAdmin;

    @FXML
    private HBox boxBtns;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TableColumn<User, Integer> colUserId;

    @FXML
    private TableColumn<User, String > colUsername;

    @FXML
    private TableColumn<User, String> colFirstName;

    @FXML
    private TableColumn<User, String> colLastName;

    @FXML
    private TableColumn<User, String> colAdminType;

    @FXML
    private TableColumn<User, Double> colPhoneNumber;

    @FXML
    private TextField tfEditUserUsername;

    @FXML
    private Label lblEditCheckUsername;

    @FXML
    private TextField tfEditUserFirstName;

    @FXML
    private TextField tfEditUserLastName;

    @FXML
    private TextField tfEditUserPhoneNumber;

    @FXML
    private TextField tfEditUserAdminType;

    @FXML
    private ImageView imgPhoto1;

    @FXML
    private ImageView imgEditPhoto;

    @FXML
    private JFXButton btnEditPhoto;

    @FXML
    private JFXCheckBox chkEditPatient;

    @FXML
    private JFXCheckBox chkEditMedicRecord;

    @FXML
    private JFXCheckBox chkEditMedicine;

    @FXML
    private JFXCheckBox chkEditGuestBook;

    @FXML
    private JFXCheckBox chkEditPayment;

    @FXML
    private JFXCheckBox chkEditReport;

    @FXML
    private JFXCheckBox chkEditVet;

    @FXML
    private JFXCheckBox chkEditAdmin;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEditSave;

    @FXML
    private TableView<User> tblCpUsers;

    @FXML
    private TextField tfCpUsername;

    @FXML
    private Label lblCheckOldPassword;

    @FXML
    private Label lblCheckNewPassword;

    @FXML
    private PasswordField pfCpOldPassword;

    @FXML
    private PasswordField pfCpNewPassword;

    @FXML
    private PasswordField pfCpNewPasswordRe;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    private TableColumn<User, Integer> colCpUserId;

    @FXML
    private TableColumn<User, String> colCpUsername;

    @FXML
    private TableColumn<User, String> colCpFirstName;

    @FXML
    private TableColumn<User, String> colCpLastName;

    @FXML
    private TableColumn<User, String> colCpAdminType;

    @FXML
    private TableColumn<User, Double> colCpPhoneNumber;


    private File photo = null;

    private int userId=0;

    private ObservableList<User> usersList = FXCollections.observableArrayList();

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
        tfEditUserPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfEditUserPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
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
        pfCpNewPasswordRe.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                checkInputNewPassword();
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
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));

        btnPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                photo = choosePhoto.showOpenDialog(new Stage());
                if (photo != null) {
                    imgPhoto.setImage(new Image(photo.toURI().toString()));
                    imgAddPhoto.setOpacity(0);
                    btnPhoto.setText("Ganti Photo");
                }
            }
        });
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
        //end of user photo

        //btn save
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isFieldNull() && checkInputPassword() && checkUsername()) {
                    if (saveUser()) {
                        Message.showSuccess();
                        clearForm();
                    } else {
                        Message.showFailed();
                    }
                }
            }
        });

        //END OF USER REGISTRATION

        //EDIT USER DATA

        //user table
        tblUsers.setItems(usersList);
        colUserId.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));
        colAdminType.setCellValueFactory(new PropertyValueFactory<User,String>("type"));
        colUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<User,Double>("phoneNumber"));
        loadUsersTable();

        //move data into form when table clicked
        tblUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tblUsers.getSelectionModel().getSelectedItem() != null){
                    User user = tblUsers.getSelectionModel().getSelectedItem();
                    userId= user.getId();
                    loadUserFields(user);
                    loadUserPhoto(user);
                    loadUserPrivilege(user);
                }
            }
        });

        //save edited user
        btnEditSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isEditFieldNull() && checkUsername() && checkInputNewPassword()) {
                    if (saveEditedUser()) {
                        Message.showSuccess();
                        loadUsersTable();
                    } else {
                        Message.showFailed();
                    }
                }
            }
        });

        //delete user
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(userId==1000) Message.showFailed("Tidak bisa menghapus Admin Utama ! ");
                else if(ConfirmationDialog.showDelete("Hapus pengguna dengan id? ("+userId+")")){
                    if(UserDao.deleteUser(userId)){
                        Message.showSuccess("Pengguna berhasil di hapus");
                        loadUsersTable();
                        clearEditForm();
                    }
                    else{
                        Message.showFailed("Terjadi kesalahan. Pengguna gagal di hapus");
                    }
                }
            }
        });

        //Change user password
       // boxUsersTable.getChildren().add(tblUsers);
        tblCpUsers.setItems(usersList);
        colCpUserId.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        colCpFirstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));
        colCpLastName.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));
        colCpAdminType.setCellValueFactory(new PropertyValueFactory<User,String>("type"));
        colCpUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        colCpPhoneNumber.setCellValueFactory(new PropertyValueFactory<User,Double>("phoneNumber"));

        //move username to field when table clicked
        tblCpUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tblCpUsers.getSelectionModel().getSelectedItem() != null){
                    User user = tblCpUsers.getSelectionModel().getSelectedItem();
                    tfCpUsername.setText(user.getUsername());
                    userId = user.getId();
                }
            }
        });
        // check password
       btnChangePassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
                   if (!isCpFieldNull() && checkOldPassword() && !tfCpUsername.getText().equalsIgnoreCase("")) {
                       if (saveNewPassword()) {
                           Message.showSuccess("Password berhasil di ganti ");
                           cleanCpField();
                       } else {
                           Message.showFailed("Terjadi kesalahan. Password gagal di ganti");
                       }
                   }
           }
       });
    }


    private boolean checkInputPassword() {
        if (!pfUserPassword.getText().equals(pfUserPasswordRe.getText())) {
            lblCheckPassword.setText("Password Tidak Sama");
            lblCheckPassword.setVisible(true);
            pfUserPasswordRe.setStyle("fx-text-fill:red;");
            return false;
        } else {
            lblCheckPassword.setVisible(false);
            pfUserPasswordRe.setStyle("fx-text-fill:silver;");
            return true;
        }
    }

    private boolean checkUsername() {
        if (UserDao.checkIsRegistered(tfUserUsername.getText())) {
            lblCheckUsername.setText("Username sudah terdaftar");
            lblCheckUsername.setVisible(true);
            return false;
        } else {
            lblCheckUsername.setVisible(false);
            return true;
        }
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

    private String getPrivilege() {
        String privilege = "";
        if (chkPatient.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkMedicRecord.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkPayment.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkReport.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkGuestBook.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkMedicine.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkVet.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkAdmin.isSelected()) privilege += '1';
        else privilege += '0';
        return privilege;
    }

    private boolean saveUser() {
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
        String fileName = saveLocalUserPhotos(userId, user.getUsername());
        UserDao.updateUserPhoto(userId, fileName);
        return userId != 0;
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

    //END OF USER REGISTRATION

    private void loadUsersTable(){
        usersList.setAll(UserDao.getAllUser());
    }

    private void loadUserFields(User user){
        tfEditUserFirstName.setText(user.getFirstName());
        tfEditUserLastName.setText(user.getLastName());
        tfEditUserAdminType.setText(user.getType());
        tfEditUserPhoneNumber.setText(String.format("%.0f",user.getPhoneNumber()));
        tfEditUserUsername.setText(user.getUsername());

    }
    private void loadUserPrivilege(User user){
        String privilege = user.getPrivilege();
        if(privilege.charAt(0) == '1') chkEditPatient.setSelected(true);
        else chkEditPatient.setSelected(false);
        if(privilege.charAt(1) == '1') chkEditMedicRecord.setSelected(true);
        else chkEditMedicRecord.setSelected(false);
        if(privilege.charAt(2) == '1') chkEditPayment.setSelected(true);
        else chkEditPayment.setSelected(false);
        if(privilege.charAt(3) == '1') chkEditReport.setSelected(true);
        else chkEditReport.setSelected(false);
        if(privilege.charAt(4) == '1') chkEditGuestBook.setSelected(true);
        else chkEditGuestBook.setSelected(false);
        if(privilege.charAt(5) == '1') chkEditMedicine.setSelected(true);
        else chkEditMedicine.setSelected(false);
        if(privilege.charAt(6) == '1') chkEditVet.setSelected(true);
        else chkEditVet.setSelected(false);
        if(privilege.charAt(7) == '1') chkEditAdmin.setSelected(true);
        else chkEditAdmin.setSelected(false);
    }
    private void loadUserPhoto(User user){
        File photo = new File("files/photos/admin/"+user.getPhoto());
        if(photo.exists()){
            imgPhoto1.setImage(new Image(photo.toURI().toString()));
            imgEditPhoto.setOpacity(0);
        }
        else imgPhoto1.setImage(null);
    }
    
    //

    private boolean saveEditedUser() {
        if(userId==1000) checkAllPrivilege();
        User user = new User();
        user.setFirstName(tfEditUserFirstName.getText());
        user.setLastName(tfEditUserLastName.getText());
        user.setPhoneNumber(Double.parseDouble(tfEditUserPhoneNumber.getText()));
        user.setUsername(tfEditUserUsername.getText());
        user.setType(tfEditUserAdminType.getText());
        user.setPrivilege(getEditedPrivilege());
        user.setId(userId);
        if(photo != null) {
            String fileName = saveLocalUserPhotos(userId, user.getUsername());
            UserDao.updateUserPhoto(userId, fileName);
        }
        return UserDao.updateUser(user);
    }

    private String getEditedPrivilege() {
        String privilege = "";
        if (chkEditPatient.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditMedicRecord.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditPayment.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditReport.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditGuestBook.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditMedicine.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditVet.isSelected()) privilege += '1';
        else privilege += '0';
        if (chkEditAdmin.isSelected()) privilege += '1';
        else privilege += '0';
        return privilege;
    }

    private boolean isFieldNull(){
        boolean status = false;
        if(tfUserUsername.getText().equalsIgnoreCase("")){
            tfUserUsername.setPromptText("Username tidak boleh kosong");
            tfUserUsername.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfUserFirstName.getText().equalsIgnoreCase("")){
            tfUserFirstName.setPromptText("Nama Depan tidak boleh kosong");
            tfUserFirstName.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfUserLastName.getText().equalsIgnoreCase("")){
            tfUserLastName.setPromptText("Nama Belakang tidak boleh kosong");
            tfUserLastName.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfUserPhoneNumber.getText().equalsIgnoreCase("")){
            tfUserPhoneNumber.setPromptText("Nomer Telpon tidak boleh kosong");
            tfUserPhoneNumber.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(tfUserAdminType.getText().equalsIgnoreCase("")){
            tfUserAdminType.setPromptText("Tipe Admin tidak boleh kosong");
            tfUserAdminType.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(pfUserPassword.getText().equalsIgnoreCase("")){
            pfUserPassword.setPromptText("Password tidak boleh kosong");
            pfUserPassword.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }

        if(pfUserPasswordRe.getText().equalsIgnoreCase("")){
            pfUserPasswordRe.setPromptText("Ulangi Password");
            pfUserPasswordRe.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }
        if(photo == null){
            btnPhoto.setStyle("-fx-text-fill:red");
            status = true;
        }
        if(!status){
            btnPhoto.setStyle("-fx-text-fill:black");
            tfUserUsername.setPromptText("Username");
            tfUserUsername.setStyle("-fx-prompt-text-fill:silver");
            tfUserFirstName.setPromptText("Nama Depan");
            tfUserFirstName.setStyle("-fx-prompt-text-fill:silver");
            tfUserLastName.setPromptText("Nama Belakang");
            tfUserLastName.setStyle("-fx-prompt-text-fill:silver");
            tfUserPhoneNumber.setPromptText("Nomer Telpon");
            tfUserPhoneNumber.setStyle("-fx-prompt-text-fill:silver");
            tfUserAdminType.setPromptText("Tipe Admin");
            tfUserAdminType.setStyle("-fx-prompt-text-fill:silver");
            pfUserPassword.setPromptText("Password");
            pfUserPassword.setStyle("-fx-prompt-text-fill:silver");
            pfUserPasswordRe.setPromptText("Ulangi Password");
            pfUserPasswordRe.setStyle("-fx-prompt-text-fill:silver");
            btnPhoto.setStyle("-fx-text-fill:black");
        }
    return status;
    }

    private void clearForm(){
        tfUserUsername.setText("");
        tfUserFirstName.setText("");
        tfUserLastName.setText("");
        tfUserPhoneNumber.setText("");
        tfUserAdminType.setText("");
        pfUserPassword.setText("");
        pfUserPasswordRe.setText("");
        photo = null;
        imgPhoto.setImage(null);
        chkMedicine.setSelected(false);
        chkAdmin.setSelected(false);
        chkVet.setSelected(false);
        chkReport.setSelected(false);
        chkPayment.setSelected(false);
        chkMedicRecord.setSelected(false);
        chkPatient.setSelected(false);
        chkGuestBook.setSelected(false);
    }
    private void clearEditForm(){
        tfEditUserUsername.setText("");
        tfEditUserFirstName.setText("");
        tfEditUserLastName.setText("");
        tfEditUserPhoneNumber.setText("");
        tfEditUserAdminType.setText("");
        photo = null;
        imgPhoto1.setImage(null);
        chkEditMedicine.setSelected(false);
        chkEditAdmin.setSelected(false);
        chkEditVet.setSelected(false);
        chkEditReport.setSelected(false);
        chkEditPayment.setSelected(false);
        chkEditMedicRecord.setSelected(false);
        chkEditPatient.setSelected(false);
        chkEditGuestBook.setSelected(false);
    }

    private void checkAllPrivilege(){
        chkEditMedicine.setSelected(true);
        chkEditAdmin.setSelected(true);
        chkEditVet.setSelected(true);
        chkEditReport.setSelected(true);
        chkEditPayment.setSelected(true);
        chkEditMedicRecord.setSelected(true);
        chkEditPatient.setSelected(true);
        chkEditGuestBook.setSelected(true);
    }

    private boolean isEditFieldNull(){
        boolean status = false;
        if(tfEditUserUsername.getText().equalsIgnoreCase("")){
            tfEditUserUsername.setPromptText("Username tidak boleh kosong");
            tfEditUserUsername.setStyle("-fx-prompt-text-fill:red");
            status = true;
        }

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
            tfEditUserUsername.setPromptText("Username");
            tfEditUserUsername.setStyle("-fx-prompt-text-fill:silver");
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

    // Change Password
    //check is old password right
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
    //check if fields null
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
            pfCpOldPassword.setStyle("-fx-prompt-text-fill:black");
            pfCpNewPassword.setPromptText("Password Baru");
            pfCpNewPassword.setStyle("-fx-prompt-text-fill:black");
            pfCpNewPasswordRe.setPromptText("Ulangi Password Baru ");
            pfCpNewPasswordRe.setStyle("-fx-prompt-text-fill:black");
        }
        return status;
    }

    private void cleanCpField(){
        pfCpNewPasswordRe.setText("");
        pfCpNewPassword.setText("");
        pfCpOldPassword.setText("");
    }

    private boolean saveNewPassword(){
        String salt = ValidateUser.getSalt().toString();
        String hash = ValidateUser.generatePassword(pfCpNewPassword.getText(),salt.getBytes(StandardCharsets.UTF_8));
        String username = tfCpUsername.getText();
        return UserDao.updatePassword(username,hash,salt.toString());
    }


}
