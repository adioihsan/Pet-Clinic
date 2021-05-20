package com.pet.clinic.controller;

import com.jfoenix.controls.JFXButton;
import com.pet.clinic.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.IOException;

public class DashboardController {

    @FXML
    private JFXButton btnPatient;

    @FXML
    void initialize(){
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
