package com.pet.clinic.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class LeftMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPatient;

    @FXML
    public JFXHamburger hmbMenu;

    @FXML
    void initialize() {
        btnDashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("patient");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
