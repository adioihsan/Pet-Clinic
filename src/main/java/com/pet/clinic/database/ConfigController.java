package com.pet.clinic.database;

import java.net.URL;
import java.util.ResourceBundle;

import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ConfigController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox chkIsLastCon;

    @FXML
    private Label lblUsedConnection;

    @FXML
    private CheckBox chkIsNewCon;

    @FXML
    private TextField tfHost;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPort;

    @FXML
    private TextField tfDatabase;

    @FXML
    private Button btnUseCon;

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

    @FXML
    private Button btnCleanHistory;

    private ObservableList<ConfigVar> configList = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        //get used connection
        lblUsedConnection.setText(Config.getConnectionUsed().toString());
        chkIsLastCon.setSelected(true);
        enableFields(false);
        //if one of check button clicked
        EventHandler<MouseEvent> chkClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getSource() == chkIsLastCon){
                    chkIsNewCon.setSelected(false);
                    enableFields(false);
                }
                else{
                    chkIsLastCon.setSelected(false);
                    enableFields(true);
                }
            }
        };
        chkIsLastCon.setOnMouseClicked(chkClicked);
        chkIsNewCon.setOnMouseClicked(chkClicked);
        //connection history
        loadConfigHistory();
        colConId.setCellValueFactory(new PropertyValueFactory<ConfigVar,Integer>("connectionId"));
        colConHost.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbHost"));
        colConPort.setCellValueFactory(new PropertyValueFactory<ConfigVar,Integer>("dbPort"));
        colConUser.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbUser"));
        colConDb.setCellValueFactory(new PropertyValueFactory<ConfigVar,String>("dbName"));

        //click on connection history
        tblConHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 && tblConHistory.getSelectionModel().getSelectedItem() != null){
                    ConfigVar configVar1 = tblConHistory.getSelectionModel().getSelectedItem();
                    if (checkConnection(configVar1)) {
                        if (ConfirmationDialog.showMakeSure("Koneksi ("+configVar1.getConnectionId()
                                +") Berhasil. Gunakan Koneksi ?"))
                                Config.setUsed(configVar1);
                                Config.setActive(configVar1);
                                lblUsedConnection.setText(configVar1.toString());
                    } else {
                        Message.showFailed("Koneksi ("+configVar1.getConnectionId()+") gagal !");
                    }
                }
            }
        });
        btnCleanHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(ConfirmationDialog.showDelete("Hapus Riwayat Koneksi ? ")) {
                    Config.deleteHistory();
                    loadConfigHistory();
                }
            }
        });

        //add connection
        btnUseCon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isFieldNull()){
                    ConfigVar configVar = setConfigVar();
                    Config.setUsed(configVar);
                    if(checkConnection(configVar)){
                        if(prepareConnection(configVar)) {
                            if (ConfirmationDialog.showMakeSure("Koneksi berhasil. Gunakan koneksi?")) {
                                int configId = Config.insertConfig(configVar);
                                configVar.setConnectionId(configId);
                                Config.setActive(configVar);
                                loadConfigHistory();
                                lblUsedConnection.setText(configVar.toString());
                            };
                        }
                    }
                    else{
                        Message.showFailed("Koneksi ke database gagal !");
                    }
                }
            }
        });

    }
    private void loadConfigHistory(){
        configList.setAll(Config.getConfigsHistory());
        tblConHistory.setItems(configList);
    }
    private ConfigVar setConfigVar(){
        ConfigVar configVar = new ConfigVar();
        configVar.setDbHost(tfHost.getText());
        configVar.setDbPort(Integer.parseInt(tfPort.getText()));
        configVar.setDbName(tfDatabase.getText());
        configVar.setDbUser(tfUsername.getText());
        configVar.setDbPass(pfPassword.getText());
        return  configVar;
    }
    private boolean checkConnection(ConfigVar cv){
        return DbConnect.testConnection(cv);
    }

    private boolean prepareConnection(ConfigVar cv) {
        if (cv.getDbName().equalsIgnoreCase("")) {
            if (ConfirmationDialog.showDialog("Database tidak ada. Buat database ?", "catatan : " +
                    "database akan di buat otomatis dengan nama \' pet_clinic \'")) {
                cv.setDbName("pet_clinic");
                if(ConfirmationDialog.showDialog("Sertakan Contoh Data ?","catatan : Beberapa contoh " +
                       "data akan di tambahkan ke database")){
                        if(DbConnect.runScript("conf/petclinic-createDbWithSample.sql")) return true;
                }
                else{
                    if(DbConnect.runScript("conf/petclinic-createDb.sql")) return true;
                }
                Message.showFailed("Terjadi kesalahan . Database gagal di buat !");
            }
            Message.showFailed("Koneksi gagal. Database tidak ada ! ");
            return false;
        }
        else{
            if(!DbConnect.checkTables()){
                if(ConfirmationDialog.showDialog("Data kosong. Tambahkan contah Data ?","catatan : " +
                        "Beberapa contoh data akan di tambahkan ke database")){
                        if(DbConnect.runScript("conf/createTableWithSample.sql")) return true;
                }
                else{
                    if(DbConnect.runScript("conf/createTable.sql")) return true;
                }
                Message.showFailed("Terjadi kesalahan. Inisialisasi tabel gagal");
            }
            else return true;
        }
        return false;
    }

    private int saveConnection(ConfigVar cv){
       return Config.insertConfig(cv);
    }


    private boolean isFieldNull(){
        boolean status = false;
        if(tfHost.getText().equalsIgnoreCase("")){
            tfHost.setStyle("-fx-border-color:red");
            status = true;
        }
        else{
            tfHost.setStyle("-fx-border-color:silver");
        }
/*        if(tfDatabase.getText().equalsIgnoreCase("")){
            tfDatabase.setStyle("-fx-border-color:red");
            status = true;
        }
        else{
            tfDatabase.setStyle("-fx-border-color:silver");
        }*/
        if(tfPort.getText().equalsIgnoreCase("")){
            tfPort.setStyle("-fx-border-color:red");
            status = true;
        }
        else {
            tfPort.setStyle("-fx-border-color:silver");
        }
        if(tfUsername.getText().equalsIgnoreCase("")){
            tfUsername.setStyle("-fx-border-color:red");
            status = true;
        }
        else{
            tfUsername.setStyle("-fx-border-color:silver");
        }
/*        if(pfPassword.getText().equalsIgnoreCase("")){
            pfPassword.setStyle("-fx-border-color:red");
            status = true;
        }
        else {
            pfPassword.setStyle("-fx-border-color:silver");
        }*/
        return status;
    }

/*
    private boolean checkDB(){

    };
*/

    private void enableFields(boolean is){
        tfUsername.setEditable(is);
        tfDatabase.setEditable(is);
        tfPort.setEditable(is);
        tfPort.setEditable(is);
        tfHost.setEditable(is);
        pfPassword.setEditable(is);
        btnUseCon.setMouseTransparent(!is);
    }

}
