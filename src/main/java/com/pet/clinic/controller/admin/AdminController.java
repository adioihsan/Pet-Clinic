package com.pet.clinic.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.pet.clinic.App;
import com.pet.clinic.controller.navigation.LeftMenuController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXDrawer menuDrawer;

    @FXML
    private JFXButton btnDynamicData;

    @FXML
    private VBox sideBoxFake;

    @FXML
    private JFXHamburger hmbFake;

    @FXML
    private JFXButton btnUserSetting;

    @FXML
    void initialize() {
        //side Menu
        URL fxmllocation = App.class.getResource("view/navigation/leftMenu.fxml");
        FXMLLoader loadLeftMenu = new FXMLLoader(fxmllocation);
        VBox leftMenu = null;
        try {
            leftMenu = loadLeftMenu.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuDrawer.setSidePane(leftMenu);
        menuDrawer.setVisible(false);
        LeftMenuController leftMenuController;
        leftMenuController = loadLeftMenu.getController();
        leftMenuController.removeActive();
        leftMenuController.setActive("admin");
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
        menuDrawer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuDrawer.close();
            }
        });
        menuDrawer.setOnDrawerClosed(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent jfxDrawerEvent) {
                sideBoxFake.setVisible(true);
                menuDrawer.setVisible(false);
            }
        });
        // main menu
        setCenterPane("dynamicData");
        btnDynamicData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeActive();
                setActive("dynamicData");
                setCenterPane("dynamicData");
            }
        });
        btnUserSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeActive();
                setActive("userSetting");
                setCenterPane("userSetting");
            }
        });

    }
    private void setCenterPane(String paneName){
        URL fxmlLocation = App.class.getResource("view/admin/"+paneName+".fxml");
        FXMLLoader loadPane = new FXMLLoader(fxmlLocation);
        try {
            VBox thePane = loadPane.load();
            mainPane.setCenter(thePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeActive(){
        btnDynamicData.setOpacity(0);
        btnUserSetting.setOpacity(0);

    }
    public void setActive(String btnActive){
        switch (btnActive){
            case "dynamicData":
                btnDynamicData.setOpacity(0.5);
                break;
            case "userSetting":
                btnUserSetting.setOpacity(0.5);
                break;
            default:
                System.out.println("Cant Find Button");
                break;
        }
    }
}
