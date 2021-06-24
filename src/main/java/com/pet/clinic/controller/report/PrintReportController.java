package com.pet.clinic.controller.report;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.pet.clinic.App;
import com.pet.clinic.database.DbConnect;
import com.pet.clinic.helper.Report;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class PrintReportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private JFXButton btnPetData;

    @FXML
    private JFXButton btnOwnerData;

    @FXML
    private JFXButton btnVetData;

    @FXML
    void initialize() {
        btnPetData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                URL url = App.class.getResource("report/patient/pet-data.jasper");
                printReport(url);
            }
        });
        btnOwnerData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                URL url = App.class.getResource("report/patient/owner-data.jasper");
                printReport(url);
            }
        });
        btnVetData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                URL url = App.class.getResource("report/patient/veterinarian-data.jasper");
                printReport(url);
            }
        });
    }
    private void printReport(URL url){
        Map map = new HashMap<String,Object>();
        Report.createReport(DbConnect.getConnection(),map,url);
        Report.showReport();
    }
}
