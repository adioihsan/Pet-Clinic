package com.pet.clinic.controller.veterinarian;

import com.jfoenix.controls.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.model.Veterinarian;
import com.pet.clinic.model.dao.VeterinarianDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

public class VeterinarianDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private JFXTreeTableView<Vets> tableView;

    @FXML
    private Label lblId;

    @FXML
    private GridPane panePetPhoto;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private ImageView imgAddPhoto;

    @FXML
    private JFXButton btnPhoto;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfSpecialist;

    @FXML
    private DateTimePicker dpDob;

    @FXML
    private JFXComboBox<String> comGender;

    @FXML
    private TextField tfPhone;

    @FXML
    private JFXTextArea taAddress;

    @FXML
    private HBox boxBtns;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    private File photo;

    @FXML
    void initialize() {

        //set gender
        comGender.getItems().add("Laki-Laki");
        comGender.getItems().add("Perempuan");

        //Craeate table column
        JFXTreeTableColumn<Vets, String> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setPrefWidth(80);
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(idCol.validateValue(param)) return param.getValue().getValue().id;
            else return idCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> firstNameCol = new JFXTreeTableColumn<>("Nama Depan");
        firstNameCol.setPrefWidth(80);
        firstNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(firstNameCol.validateValue(param)) return param.getValue().getValue().firstName;
            else return firstNameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> lastNameCol = new JFXTreeTableColumn<>("Nama Belakang");
        lastNameCol.setPrefWidth(100);
        lastNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(lastNameCol.validateValue(param)) return param.getValue().getValue().lastName;
            else return lastNameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> titleCol = new JFXTreeTableColumn<>("Gelar");
        titleCol.setPrefWidth(100);
        titleCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(titleCol.validateValue(param)) return param.getValue().getValue().title;
            else return titleCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> specialistCol = new JFXTreeTableColumn<>("Spesialist");
        specialistCol.setPrefWidth(100);
        specialistCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(specialistCol.validateValue(param)) return param.getValue().getValue().specialist;
            else return specialistCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> dobCol = new JFXTreeTableColumn<>("Tanggal Lahir");
        dobCol.setPrefWidth(110);
        dobCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(dobCol.validateValue(param)) return param.getValue().getValue().dob;
            else return dobCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> genderCol = new JFXTreeTableColumn<>("Jenis Kelamin");
        genderCol.setPrefWidth(110);
        genderCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(genderCol.validateValue(param)) return param.getValue().getValue().gender;
            else return genderCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> phoneNumberCol = new JFXTreeTableColumn<>("Warna");
        phoneNumberCol.setPrefWidth(100);
        phoneNumberCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(phoneNumberCol.validateValue(param)) return param.getValue().getValue().phoneNumber;
            else return phoneNumberCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Vets, String> addressCol = new JFXTreeTableColumn<>("Tanggal Terdaftar");
        addressCol.setPrefWidth(175);
        addressCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Vets, String> param) ->{
            if(addressCol.validateValue(param)) return param.getValue().getValue().address;
            else return addressCol.getComputedValue(param);
        });
        //Add columns to table
        tableView.getColumns().setAll(idCol,firstNameCol,lastNameCol,titleCol,specialistCol,dobCol,
                genderCol,phoneNumberCol,addressCol);

        //set table rules
        tableView.setShowRoot(false);
        tableView.setEditable(false);

        //load table data
        loadTableData();

        // Table on click
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tableView.getSelectionModel().getSelectedItem() != null){
                    Vets vet = tableView.getSelectionModel().getSelectedItem().getValue();
                    int id = Integer.valueOf(vet.id.getValue().toString());
                    getVetDetail(id);
                }
            }
        });

        //photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png"));

        btnPhoto.setOpacity(0);
        imgPhoto.setOpacity(0);
        btnPhoto.setOnAction(e ->{
            photo = choosePhoto.showOpenDialog(new Stage());
            if(photo != null) {
                imgAddPhoto.setOpacity(0);
                imgPhoto.setImage(new Image(photo.toURI().toString()));
            }
        });

        btnPhoto.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imgAddPhoto.setOpacity(0.5);
                btnPhoto.setOpacity(1);
            }
        });

        btnPhoto.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imgAddPhoto.setOpacity(0);
                btnPhoto.setOpacity(0);
            }
        });

        //buttons
        boxBtns.getChildren().setAll(btnEdit,btnDelete);
        setEditableForm(false , 0.5);
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boxBtns.getChildren().setAll(btnSave,btnDelete);
                setEditableForm(true , 1.0);
            }
        });
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boxBtns.getChildren().setAll(btnEdit,btnDelete);
                setEditableForm(false , 0.5);
                boolean status = saveVeterinarian(Integer.valueOf(lblId.getText()));
                if(status){
                    Alert success = new Alert(Alert.AlertType.INFORMATION,"Data Berhasil di Ubah");
                    success.show();
                    loadTableData();
                }
                else{
                    Alert failed = new Alert(Alert.AlertType.ERROR,"Terjadi Kesalahan. Data Gagal di ubah");
                    failed.show();
                }

            }
        });
        btnDelete.setOnAction(e->{
           boolean status = deleteVet(Integer.valueOf(lblId.getText()));
            if(status){
                Alert success = new Alert(Alert.AlertType.INFORMATION,"Data Berhasil di Hapus");
                success.show();
                loadTableData();
            }
            else{
                Alert failed = new Alert(Alert.AlertType.ERROR,"Terjadi Kesalahan. Data Gagal di Hapus");
                failed.show();
            }
        });


    }

    private void loadTableData(){
        ArrayList<Veterinarian> vetsFromDb = VeterinarianDao.getAllVet();
        ObservableList<Vets> vetsList = FXCollections.observableArrayList();
        Iterator<Veterinarian> itr = vetsFromDb.iterator();
        while(itr.hasNext()){
            Veterinarian vet = itr.next();
            vetsList.add(new Vets( String.valueOf(vet.getId()),vet.getFirstName(),vet.getLastName(),
                    vet.getTitle(),vet.getSpecialist(),String.valueOf(vet.getDob()),
                    vet.getGender(),String.valueOf(vet.getPhoneNumber()),vet.getAddress()));
        }
        TreeItem<Vets> root = new RecursiveTreeItem<Vets>(vetsList, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
    }

    private void getVetDetail(int id){
        Veterinarian vet = VeterinarianDao.getVeterinarian(id);
        lblId.setText(String.valueOf(vet.getId()));
        tfFirstName.setText(vet.getFirstName());
        tfLastName.setText(vet.getLastName());
        tfTitle.setText(vet.getTitle());
        tfSpecialist.setText(vet.getSpecialist());
        dpDob.setValue(vet.getDob());
        comGender.setValue(vet.getGender());
        tfPhone.setText(String.valueOf(vet.getPhoneNumber()));
        taAddress.setText(vet.getAddress());
        setPhoto(vet.getPhoto());

    }

    private boolean saveVeterinarian(int id){
        boolean status = false;
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(id);
        veterinarian.setFirstName(tfFirstName.getText());
        veterinarian.setLastName(tfLastName.getText());
        veterinarian.setTitle(tfTitle.getText());
        veterinarian.setSpecialist(tfSpecialist.getText());
        veterinarian.setDob(dpDob.getValue());
        veterinarian.setGender(comGender.getValue());
        veterinarian.setPhoneNumber(Double.valueOf(tfPhone.getText()));
        veterinarian.setAddress(taAddress.getText());
        status = VeterinarianDao.updateVeterinarian(veterinarian);
        return status;
    }

    private boolean deleteVet(int id){
        boolean status = false;
        status = VeterinarianDao.deleteVeterinarian(id);
        return status;
    }

    private void setPhoto(String photoName){
        File photo = new File("files/photos/veterinarian/"+photoName);
        if(photo != null){
            imgAddPhoto.setOpacity(0);
            imgPhoto.setImage(new Image(photo.toURI().toString()));
            imgPhoto.setOpacity(1);
        }
    }

    private void setEditableForm(boolean isEditable , double opacity){
        btnPhoto.setVisible(isEditable);
        tfFirstName.setEditable(isEditable);
        tfLastName.setEditable(isEditable);
        tfSpecialist.setEditable(isEditable);
        tfTitle.setEditable(isEditable);
        comGender.setDisable(!isEditable);
        dpDob.setDisable(!isEditable);
        tfPhone.setEditable(isEditable);
        taAddress.setOpacity(opacity);
        tfFirstName.setOpacity(opacity);
        tfLastName.setOpacity(opacity);
        tfSpecialist.setOpacity(opacity);
        tfTitle.setOpacity(opacity);
        tfPhone.setOpacity(opacity);
    }

}
class Vets extends RecursiveTreeObject<Vets> {
    StringProperty id;
    StringProperty firstName;
    StringProperty lastName;
    StringProperty title;
    StringProperty specialist;
    StringProperty dob;
    StringProperty gender;
    StringProperty phoneNumber;
    StringProperty address;

    public Vets(String id, String firstName, String lastName,
                String title, String specialist, String dob,
                String gender, String phoneNumber, String address) {
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.title = new SimpleStringProperty(title);
        this.specialist = new SimpleStringProperty(specialist);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
    }

}

