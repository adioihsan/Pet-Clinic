package com.pet.clinic.controller.dashboard;

import com.pet.clinic.App;
import com.pet.clinic.controller.navigation.LeftMenuController;
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
        URL fxmllocation = App.class.getResource("view/navigation/leftMenu.fxml");
        FXMLLoader loadLeftMenu = new FXMLLoader(fxmllocation);
        VBox leftMenu = loadLeftMenu.load();
        mainPane.setLeft(leftMenu);
        //get left menu controller
        LeftMenuController leftMenuController;
        leftMenuController = loadLeftMenu.getController();
        leftMenuController.hmbMenu.setVisible(false);
        leftMenuController.removeActive();
        leftMenuController.setActive("dashboard");

        //main
        setCenterPane("dashboardMain");

    }

    private void setCenterPane(String paneName){
        URL fxmlLocation = App.class.getResource("view/dashboard/"+paneName+".fxml");
        FXMLLoader loadPane = new FXMLLoader(fxmlLocation);
        try {
            VBox thePane = loadPane.load();
            mainPane.setCenter(thePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
