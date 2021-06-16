package com.pet.clinic.controller.medicRecord;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PrescriptionPopupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Label lblName;

    @FXML
    private Label lblUnit;

    @FXML
    private Label lblStock;

    @FXML
    private TextField tfAmount;

    @FXML
    private TextField tfDescription;

    @FXML
    private JFXButton btnOK;

    @FXML
    void initialize() {

        tfAmount.setText("1");
        tfAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfAmount.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });
    tfAmount.setOnKeyTyped(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if(!tfAmount.getText().equalsIgnoreCase("")) {
                if (Integer.parseInt(tfAmount.getText()) > Integer.valueOf(lblStock.getText())) {
                    tfAmount.setText(lblStock.getText());
                }
                if(Integer.parseInt(tfAmount.getText()) == 0){
                    tfAmount.setText("1");
                }
            }
        }
    });
    }
    public void setMedicineName(String name){
        lblName.setText(name);
    }
    public void setUnit(String name){
        lblUnit.setText(name);
    }
    public void setStock(int stock){
        lblStock.setText(String.valueOf(stock));
    }
    public String getDescription(){
        return tfDescription.getText();
    }
    public int getAmount(){
        if (tfAmount.getText().equalsIgnoreCase(""))
            return 1;
        return Integer.parseInt(tfAmount.getText());
    }

    public JFXButton getBtnOK(){
        return btnOK;
    }
    public TextField getTfAmount(){
        return tfAmount;
    }
    public TextField getTfDescription(){
        return tfDescription;
    }

}
