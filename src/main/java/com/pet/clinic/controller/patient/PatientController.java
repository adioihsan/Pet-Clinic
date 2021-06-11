package com.pet.clinic.controller.patient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.pet.clinic.App;
import com.pet.clinic.controller.navigation.LeftMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnPet;

    @FXML
    private JFXButton btnPetOwner;

    @FXML
    void initialize() throws IOException {

        //side Menu
        URL fxmllocation = App.class.getResource("view/navigation/leftMenu.fxml");
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
        // Main Menu
            //set center pane to registration (default)
        setCenterPane("registration");
        btnRegister.setOnAction(event -> {
            setCenterPane("registration");
    });
        // Menu butoon click action
        btnRegister.setOnAction(e ->{
            removeActive();
            setActive("registration");
            setCenterPane("registration");
        });
        btnPet.setOnAction(event -> {
            removeActive();
            setActive("petData");
            setCenterPane("petData");
        });
        btnPetOwner.setOnAction(event ->{
            removeActive();
            setActive("ownerData");
            setCenterPane("ownerData");
        });

    }
    private void setCenterPane(String paneName){
        URL fxmllocation = App.class.getResource("view/patient/"+paneName+".fxml");
        FXMLLoader loadPane = new FXMLLoader(fxmllocation);
        try {
            VBox thePane = loadPane.load();
            mainPane.setCenter(thePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeActive(){
        btnRegister.setOpacity(0);
        btnPet.setOpacity(0);
        btnPetOwner.setOpacity(0);
    }
    public void setActive(String btnActive){
        switch (btnActive){
            case "registration":
                btnRegister.setOpacity(0.5);
                break;
            case "petData":
                btnPet.setOpacity(0.5);
                break;
            case "ownerData":
                btnPetOwner.setOpacity(0.5);
                break;
            default:
                System.out.println("Cant Find Button");
                break;
        }
    }
}
