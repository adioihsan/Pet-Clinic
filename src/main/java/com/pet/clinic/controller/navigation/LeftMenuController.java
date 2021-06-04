package com.pet.clinic.controller.navigation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import com.pet.clinic.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import com.pet.clinic.controller.login.ValidateUser;

public class LeftMenuController {

    private User loggedUser = ValidateUser.getLoggeduser();

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
    private Label lblUserType;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUsername;

    @FXML
    private JFXButton btnMedicRecord;

    @FXML
    private JFXButton btnVeterinarian;

    @FXML
    void initialize() {
        btnDashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("dashboard/dashboard");
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
                    App.setRoot("patient/patient");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnMedicRecord.setOnAction(e ->{
            try {
                App.setRoot("medicRecord/medicRecord");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        btnVeterinarian.setOnAction(e ->{
            try {
                App.setRoot("veterinarian/veterinarian");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Logged User
       imgUserCircle.setFill(new ImagePattern(imgUser.getImage()));
       lblUsername.setText(loggedUser.getUsername());
       lblUserId.setText(Integer.toString(loggedUser.getId()));
       lblUserType.setText(loggedUser.getType());
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
            case "medicRecord":
                btnMedicRecord.setOpacity(0.5);
            case "veterinarian":
                btnVeterinarian.setOpacity(0.5);
            default:
                System.out.println("Cant Find Button");
                break;
        }
    }



}
