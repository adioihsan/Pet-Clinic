package com.pet.clinic.controller.login;


import com.jfoenix.controls.JFXButton;
import com.pet.clinic.controller.login.ValidateUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import javafx.event.ActionEvent;
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
    void initialize() {
       btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               loginUser();
           }
       });
       tfPassword.setOnAction(event -> {
           loginUser();
       });
    }
    private void loginUser(){
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        if(!username.equals("") || !password.equals("")){
            if(ValidateUser.isValid(username,password)){
                try {

                    App.setRoot("dashboard/dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                lblMessage.setText("Username or Password wrong ! ");
            }
        }
        else{
            lblMessage.setText("Username or Password cant be empty !");
        }
    }

}

