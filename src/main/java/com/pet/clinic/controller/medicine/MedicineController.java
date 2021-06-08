package com.pet.clinic.controller.medicine;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MedicineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXDrawer menuDrawer;

    @FXML
    private JFXButton btnMedicine;

    @FXML
    private VBox sideBoxFake;

    @FXML
    private JFXHamburger hmbFake;

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
        leftMenuController.setActive("medicine");
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

        //main menu
        setCenterPane("medicineData");
       btnMedicine.setOnAction(e ->{
           setCenterPane("medicineData");

       });

    }

    private void setCenterPane(String paneName){
        URL fxmllocation = App.class.getResource("view/medicine/"+paneName+".fxml");
        FXMLLoader loadPane = new FXMLLoader(fxmllocation);
        try {
            VBox thePane = loadPane.load();
            mainPane.setCenter(thePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
