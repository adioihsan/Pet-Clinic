package com.pet.clinic.controller;

import com.jfoenix.controls.JFXButton;
import com.pet.clinic.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    @FXML
    private BorderPane mainPane;

    @FXML
    void initialize() throws IOException {
        URL fxmllocation = App.class.getResource("view/leftMenu.fxml");
        FXMLLoader loadLeftMenu = new FXMLLoader(fxmllocation);
        VBox leftMenu = loadLeftMenu.load();
        mainPane.setLeft(leftMenu);
        //get left menu controller
        LeftMenuController leftMenuController;
        leftMenuController = loadLeftMenu.getController();
        leftMenuController.hmbMenu.setVisible(false);
        leftMenuController.removeActive();
        leftMenuController.setActive("dashboard");


    }
}
