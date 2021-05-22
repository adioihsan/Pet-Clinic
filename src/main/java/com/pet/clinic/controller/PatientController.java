package com.pet.clinic.controller;

import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.pet.clinic.App;
import com.pet.clinic.controller.LeftMenuController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
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
    private JFXHamburger hmbFake;

    @FXML
    private VBox sideBoxFake;

    @FXML
    void initialize() throws IOException {

        //side Menu
        URL fxmllocation = App.class.getResource("view/leftMenu.fxml");
        FXMLLoader loadLeftMenu = new FXMLLoader(fxmllocation);
        VBox leftMenu = loadLeftMenu.load();
        menuDrawer.setSidePane(leftMenu);
        menuDrawer.setVisible(false);
        LeftMenuController leftMenuController;
        leftMenuController = loadLeftMenu.getController();
        leftMenuController.removeActive();
        leftMenuController.setActive("patient");

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(leftMenuController.hmbMenu);
        leftMenuController.hmbMenu.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuDrawer.close();
            }
        }));
        hmbFake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                transition.setRate(1);
                transition.play();
                menuDrawer.setVisible(true);
                menuDrawer.open();
            }
        });
        menuDrawer.setOnDrawerOpening(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent jfxDrawerEvent) {
                sideBoxFake.setVisible(false);
            }
        });
        menuDrawer.setOnDrawerClosed(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent jfxDrawerEvent) {
                sideBoxFake.setVisible(true);
                menuDrawer.setVisible(false);
            }
        });


    }
}
