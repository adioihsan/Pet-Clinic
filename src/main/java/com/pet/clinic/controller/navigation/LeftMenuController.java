package com.pet.clinic.controller.navigation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Popup;
import com.pet.clinic.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import com.pet.clinic.controller.login.ValidateUser;

public class LeftMenuController {
    @FXML
    private VBox pnMainPane;

    @FXML
    public JFXHamburger hmbMenu;

    @FXML
    private Pane pnDashboard;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private Pane pnPatient;

    @FXML
    private JFXButton btnPatient;

    @FXML
    private Pane pnMedicRecord;

    @FXML
    private JFXButton btnMedicRecord;

    @FXML
    private Pane pnPayment;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private Pane pnReport;

    @FXML
    private JFXButton btnReport;

    @FXML
    private Pane pnGuestBook;

    @FXML
    private JFXButton btnGuestBook;

    @FXML
    private Pane pnMedicine;

    @FXML
    private JFXButton btnMedicine;

    @FXML
    private Pane pnVet;

    @FXML
    private JFXButton btnVeterinarian;

    @FXML
    private Pane pnAdmin;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private Circle imgUserCircle;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserType;

    @FXML
    private JFXButton btnLogout;

    private User loggedUser = ValidateUser.getLoggedUser();

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
        btnPayment.setOnAction(e->{
            try {
                App.setRoot("payment/payment");
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
        btnMedicine.setOnAction(e -> {
            try {
                App.setRoot("medicine/medicine");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        btnGuestBook.setOnAction(e ->{
            try {
                App.setRoot("guestBook/guestBook");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        btnReport.setOnAction(e->{
            try {
                App.setRoot("report/report");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        btnAdmin.setOnAction(e->{
            try {
                App.setRoot("admin/admin");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnLogout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean isLogOut = ConfirmationDialog.showMakeSure("Yakin Keluar Dari Aplikasi ?");
                if(isLogOut) {
                    try {
                        App.setRoot("login/login");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        // Logged User
        File photo = new File("files/photos/admin/"+loggedUser.getPhoto());
        if(photo.exists()) {
            imgUserCircle.setFill(new ImagePattern(new Image(photo.toURI().toString())));
        }
       lblUsername.setText(loggedUser.getUsername());
       lblUserId.setText(Integer.toString(loggedUser.getId()));
       lblUserType.setText(loggedUser.getType());

       imgUserCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
            showUserView();
           }
       });

       //set privilege
        setPrivilege();
    }

    public void removeActive(){
       btnDashboard.setOpacity(0);
       btnPatient.setOpacity(0);
       btnMedicRecord.setOpacity(0);
       btnVeterinarian.setOpacity(0);
       btnMedicine.setOpacity(0);
       btnGuestBook.setOpacity(0);
       btnPayment.setOpacity(0);
       btnReport.setOpacity(0);
       btnAdmin.setOpacity(0);
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
                break;
            case "payment":
                btnPayment.setOpacity(0.5);
                break;
            case "report":
                btnReport.setOpacity(0.5);
                break;
            case "veterinarian":
                btnVeterinarian.setOpacity(0.5);
                break;
            case "guestBook":
                btnGuestBook.setOpacity(0.5);
                break;
            case "medicine":
                btnMedicine.setOpacity(0.5);
                break;
            case "admin":
                btnAdmin.setOpacity(0.5);
                break;
            default:
                System.out.println("Cant Find Button");
                break;
        }
    }
    public void setPrivilege(){
        String privilege = loggedUser.getPrivilege();
        if(privilege.charAt(0) == '0') pnMainPane.getChildren().remove(pnPatient);
        if(privilege.charAt(1) == '0') pnMainPane.getChildren().remove(pnMedicRecord);
        if(privilege.charAt(2) == '0') pnMainPane.getChildren().remove(pnPayment);
        if(privilege.charAt(3) == '0') pnMainPane.getChildren().remove(pnReport);
        if(privilege.charAt(4) == '0') pnMainPane.getChildren().remove(pnGuestBook);
        if(privilege.charAt(5) == '0') pnMainPane.getChildren().remove(pnMedicine);
        if(privilege.charAt(6) == '0') pnMainPane.getChildren().remove(pnVet);
        if(privilege.charAt(7) == '0') pnMainPane.getChildren().remove(pnAdmin);
    }

    public void showUserView(){
        Popup popup = new Popup();
        popup.load(imgUserCircle.getScene().getWindow(),"user/userView");
    }

}
