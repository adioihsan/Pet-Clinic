package com.pet.clinic.controller.login;


import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import com.pet.clinic.database.Config;
import com.pet.clinic.database.ConfigController;
import com.pet.clinic.database.DbConnect;
import com.pet.clinic.helper.Popup;
import com.pet.clinic.model.User;
import com.pet.clinic.model.dao.UserDao;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUsername;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private JFXButton btnSetting;

    @FXML
    void initialize() {
        Config.loadFirstConnection();
       btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               if(DbConnect.isConnected()) {
                   loginUser();
               }
           }
       });
       tfPassword.setOnAction(event -> {
           if(DbConnect.isConnected())
               loginUser();
       });
       btnSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               loadSetting();
           }
       });

    }
    private void loginUser(){
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        if(!username.equals("") || !password.equals("")){
            if(ValidateUser.isValid(username,password)){
                try {
                    User user = UserDao.getUser(username);
                    App.setRoot("dashboard/dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                lblMessage.setText("Username atau Password salah ! ");
            }
        }
        else{
            lblMessage.setText("Username atau Password tidak boleh kosong !");
        }
    }
    private void loadSetting(){
        Popup popup = new Popup();
        ConfigController dbc = (ConfigController) popup.load(btnSetting.getScene().getWindow(),"login/databaseConfig");
    }
}

