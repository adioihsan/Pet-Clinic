package com.pet.clinic.database;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConfigController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox chkIsDefault;

    @FXML
    private TextField tfHost;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPort;

    @FXML
    private TextField tfDatabase;

    @FXML
    private Button btnCheckCon;

    @FXML
    private Button btnSaveCon;

    @FXML
    private TableView<ConfigVar> tblConHistory;

    @FXML
    private TableColumn<ConfigVar, Integer> colConId;

    @FXML
    private TableColumn<ConfigVar, String> colConHost;

    @FXML
    private TableColumn<ConfigVar, Integer> colConPort;

    @FXML
    private TableColumn<ConfigVar, String> colConDb;

    @FXML
    private TableColumn<ConfigVar, String> colConUser;

    @FXML
    private PasswordField pfPassword;

    private ObservableList<ConfigVar> configList = FXCollections.observableArrayList();
    @FXML
    void initialize() {

        //connection history
        loadConfigHistory();
        colConId.setCellValueFactory(new PropertyValueFactory<ConfigVar,Integer>("connectionId"));
        colConHost.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbHost"));
        colConPort.setCellValueFactory(new PropertyValueFactory<ConfigVar,Integer>("dbPort"));
        colConUser.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbUser"));
        colConDb.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbName"));

    }
    private void loadConfigHistory(){
        configList.setAll(Config.getConfigsHistory());
        tblConHistory.setItems(configList);
    }
    private void addConnection(){
        ConfigVar configVar = new ConfigVar();
        configVar.setDbHost(tfHost.getText());
        configVar.setDbPort(Integer.parseInt(tfPort.getText()));
        configVar.setDbName(tfDatabase.getText());
        configVar.setDbUser(tfUsername.getText());
        configVar.setDbPass(pfPassword.getText());

    }
}
