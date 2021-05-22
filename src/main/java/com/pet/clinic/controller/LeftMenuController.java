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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
    private Circle imgUserCircle;

    @FXML
    private ImageView imgUser;

    @FXML
    void initialize() {
        btnDashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("dashboard");
                    removeActive();
                    btnDashboard.setOpacity(0.5);
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

        // Logged User Image
       imgUserCircle.setFill(new ImagePattern(imgUser.getImage()));

       //Active Butoon

    }

    public void removeActive(){
       btnDashboard.setOpacity(0);
       btnPatient.setOpacity(0);
    }
    public void setActive(String btnActive){
        switch (btnActive){
            case "dashboard":
                btnDashboard.setOpacity(0.5);
                break;
            case "patient":
                btnPatient.setOpacity(0.5);
                break;
            default:
                System.out.println("Cant Find Button");
                break;
        }
    }



}
