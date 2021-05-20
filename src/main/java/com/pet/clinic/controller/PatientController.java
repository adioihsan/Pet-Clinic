package com.pet.clinic.controller;

import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.pet.clinic.App;
import com.pet.clinic.controller.LeftMenuController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class PatientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXDrawer menuDrawer;

    @FXML
    private BorderPane mainPane;

    @FXML
    void initialize() throws IOException {
        URL fxmllocation = App.class.getResource("view/leftMenu.fxml");
        FXMLLoader loadLeftMenu = new FXMLLoader(fxmllocation);
        VBox leftMenu = loadLeftMenu.load();
        menuDrawer.setSidePane(leftMenu);
        LeftMenuController leftMenuController;
        leftMenuController = loadLeftMenu.getController();
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(leftMenuController.hmbMenu);
        transition.setRate(-1);
        leftMenuController.hmbMenu.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                transition.setRate(transition.getRate()*-1);
                transition.play();
                if(menuDrawer.isOpened()) {
                    menuDrawer.close();

                } else {
                    menuDrawer.open();
             ;
                }

            }
        }));

    }
}
