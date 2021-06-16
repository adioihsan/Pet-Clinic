package com.pet.clinic.controller.guestBook;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AddGuestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfPetId;

    @FXML
    private Label lblSearchStatus;

    @FXML
    private TableView<?> tblGuest;

    @FXML
    private TableColumn<?, ?> colVisitiedDate;

    @FXML
    private TableColumn<?, ?> colPetOwnerName;

    @FXML
    private TableColumn<?, ?> colPetName;

    @FXML
    private TableColumn<?, ?> colPetOwnerId;

    @FXML
    private TableColumn<?, ?> colPetId;

    @FXML
    void initialize() {

        // tf number only rules
        tfPetId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPetId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
}
