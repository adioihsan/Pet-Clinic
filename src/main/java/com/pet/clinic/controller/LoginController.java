package com.pet.clinic.controller;

import com.jfoenix.controls.JFXButton;
import com.pet.clinic.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private JFXButton btnLogin;

    public void login() throws IOException {
        App.setRoot("dashboard");
    }

}
