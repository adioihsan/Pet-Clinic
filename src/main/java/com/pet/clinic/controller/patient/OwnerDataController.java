package com.pet.clinic.controller.patient;

import com.jfoenix.controls.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.dao.PetOwnerDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

public class OwnerDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private JFXTreeTableView<Owners> tableView;

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
    private DateTimePicker dpDob;

    @FXML
    private JFXComboBox<String> comGender;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextArea taAddress;

    @FXML
    private HBox boxBtns;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField tfLimit;

    @FXML
    private JFXButton btnAddLimit;

    @FXML
    private TextField tfFind;

    private File photo;

    private JFXTreeTableColumn<Owners, String> petsCol = new JFXTreeTableColumn<>("Peliharaan");

    @FXML
    void initialize() {

        //set owner gender
        comGender.getItems().addAll("Laki-Laki","Perempuan");

        //Pet owner columns

        JFXTreeTableColumn<Owners, String> petOwnerIdCol = new JFXTreeTableColumn<>("ID Pemilik");
        petOwnerIdCol.setPrefWidth(80);
        petOwnerIdCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(petOwnerIdCol.validateValue(param)) return param.getValue().getValue().petOwnerId;
            else return petOwnerIdCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> firstNameCol = new JFXTreeTableColumn<>("Nama Depan");
        firstNameCol.setPrefWidth(80);
        firstNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(firstNameCol.validateValue(param)) return param.getValue().getValue().firstName;
            else return firstNameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> lastNameCol = new JFXTreeTableColumn<>("Nama Belakang");
        lastNameCol.setPrefWidth(100);
        lastNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(lastNameCol.validateValue(param)) return param.getValue().getValue().lastName;
            else return lastNameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> dobCol = new JFXTreeTableColumn<>("Tanggal Lahir");
        dobCol.setPrefWidth(110);
        dobCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(dobCol.validateValue(param)) return param.getValue().getValue().dob;
            else return dobCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> genderCol = new JFXTreeTableColumn<>("Jenis Kelamin");
        genderCol.setPrefWidth(110);
        genderCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(genderCol.validateValue(param)) return param.getValue().getValue().gender;
            else return genderCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> phoneNumberCol = new JFXTreeTableColumn<>("Nomer Telpon");
        phoneNumberCol.setPrefWidth(100);
        phoneNumberCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(phoneNumberCol.validateValue(param)) return param.getValue().getValue().phoneNumber;
            else return phoneNumberCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Owners, String> addressCol = new JFXTreeTableColumn<>("Alamat");
        addressCol.setPrefWidth(175);
        addressCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(addressCol.validateValue(param)) return param.getValue().getValue().address;
            else return addressCol.getComputedValue(param);
        });

        petsCol.setPrefWidth(175);
        petsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Owners, String> param) ->{
            if(addressCol.validateValue(param)) return param.getValue().getValue().pets;
            else return addressCol.getComputedValue(param);
        });

        // add columns to table
        tableView.getColumns().addAll(petOwnerIdCol,firstNameCol,lastNameCol,genderCol,dobCol,
                phoneNumberCol,addressCol,petsCol);
        // add table rules;
        tableView.setShowRoot(false);
        tableView.setEditable(true);

        //load data into table
        loadTableData(100);

        // clik table to get owner id
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tableView.getSelectionModel().getSelectedItem() != null){
                    Owners owner = tableView.getSelectionModel().getSelectedItem().getValue();
                    int petOwnerId = Integer.valueOf(owner.petOwnerId.getValue().toString());
                    setOwner(petOwnerId);
                    setPetsName(petOwnerId);
                    setEditableForm(false,0.5);
                    boxBtns.getChildren().setAll(btnEdit,btnDelete);
                }
            }
        });

        //photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png") ,
                new FileChooser.ExtensionFilter("JPG","*.JPG"),
                new FileChooser.ExtensionFilter("JPEG","*.JPEG"));

        btnPhoto.setOpacity(0);
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
                if(isFormNotNull()) {
                    boxBtns.getChildren().setAll(btnEdit, btnDelete);
                    setEditableForm(false, 0.5);
                    if (saveOwner(Integer.parseInt(lblId.getText()))) {
                        Message.showSuccess("Data Pemilik Berhasil di Ubah");
                        loadTableData(50);
                    } else {
                        Alert failed = new Alert(Alert.AlertType.ERROR, "Terjadi Kesalahan ! . Data Pemilik Gagal" +
                                " di Ubah.");
                        failed.show();
                    }
                }
            }
        });
        btnDelete.setOnAction(e->{
            PetOwner petOwner = PetOwnerDao.getOwner(Integer.valueOf(lblId.getText()));
            if(deletePetOwner(petOwner.getId())) {
                deleteLocalOwnerPhoto(petOwner.getPhoto());
                clearOwnerForm();
                Message.showSuccess("Pemilik Berhasil di Hapus.");
                loadTableData(50);
            }
            else {
                Message.showFailed("Terjadi Kesalahan ! . Pemilik Gagal di Hapus.");
            }
        });

        // find data on database
        tfFind.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int limit = Integer.parseInt(tfLimit.getText());
                loadTableData(tfFind.getText() ,limit);
                if(tfFind.getText().equalsIgnoreCase(""))
                    loadTableData(limit);
            }
        });
        tfLimit.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(tfLimit.getText().equalsIgnoreCase(""))
                    tfLimit.setText(String.valueOf(1));
                loadTableData(Integer.parseInt(tfLimit.getText()));
            }
        });
        btnAddLimit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tfLimit.setText(String.valueOf(Integer.parseInt(tfLimit.getText())+1));
                loadTableData(Integer.parseInt(tfLimit.getText()));
            }
        });

        // END OF INITIALIZE
        
    }
    private  void clearOwnerForm(){
        tfFirstName.clear();
        tfLastName.clear();
        tfPhone.clear();
        comGender.setPromptText("Jenis Kelamin");
        dpDob.setPromptText("Tanggal Lahir");
        taAddress.clear();
        dpDob.setValue(null);
        comGender.setValue(null);
        setOwnerPhoto(null);
    };

    //disable form
    private void setEditableForm(boolean isEditable , double opacity){
        btnPhoto.setVisible(isEditable);
        tfFirstName.setEditable(isEditable);
        tfLastName.setEditable(isEditable);
        tfPhone.setEditable(isEditable);
        comGender.setDisable(!isEditable);
        dpDob.setEditable(isEditable);
        taAddress.setEditable(isEditable);
        btnPhoto.setOpacity(opacity);
        tfFirstName.setOpacity(opacity);
        tfLastName.setOpacity(opacity);
        tfPhone.setOpacity(opacity);
        taAddress.setOpacity(opacity);
        dpDob.setOpacity(opacity);
    }


    private void loadTableData(int limit){
        ArrayList<PetOwner> petOwnersFromDb = PetOwnerDao.getAllPetOwner(limit);
        ObservableList<Owners> petOwnersList = FXCollections.observableArrayList();
        if(!petOwnersFromDb.isEmpty()){
            Iterator<PetOwner> itr =  petOwnersFromDb.iterator();
            while(itr.hasNext()){
                PetOwner petOwner = itr.next();
                petOwnersList.add(new Owners(String.valueOf(petOwner.getId()),petOwner.getFirstName(),
                        petOwner.getLastName(),petOwner.getDob().toString(),petOwner.getGender(),
                        String.format("%.0f",petOwner.getPhoneNumber()),petOwner.getAddress()));
            }
            TreeItem<Owners> root = new RecursiveTreeItem<Owners>(petOwnersList, RecursiveTreeObject::getChildren);
            tableView.setRoot(root);

        }
    }

    private void loadTableData(String keyword , int limit){
        ArrayList<PetOwner> petOwnersFromDb = PetOwnerDao.findPetOwners(keyword,limit);
        ObservableList<Owners> petOwnersList = FXCollections.observableArrayList();
        if(!petOwnersFromDb.isEmpty()){
            Iterator<PetOwner> itr =  petOwnersFromDb.iterator();
            while(itr.hasNext()){
                PetOwner petOwner = itr.next();
                petOwnersList.add(new Owners(String.valueOf(petOwner.getId()),petOwner.getFirstName(),
                        petOwner.getLastName(),petOwner.getDob().toString(),petOwner.getGender(),
                        String.valueOf(petOwner.getPhoneNumber()),petOwner.getAddress()));
            }
            TreeItem<Owners> root = new RecursiveTreeItem<Owners>(petOwnersList, RecursiveTreeObject::getChildren);
            tableView.setRoot(root);
        }
    }

    private boolean setOwner(int id){
        PetOwner petOwner = PetOwnerDao.getOwner(id);
        if(petOwner != null){
            lblId.setText(String.valueOf(petOwner.getId()));
            tfFirstName.setText(petOwner.getFirstName());
            tfLastName.setText(petOwner.getLastName());
            dpDob.setValue(petOwner.getDob());
            comGender.setValue(petOwner.getGender());
            tfPhone.setText(String.format("%.0f",petOwner.getPhoneNumber()));
            taAddress.setText(petOwner.getAddress());
            setOwnerPhoto(petOwner.getPhoto());
            return true;
        }
        else{
            return false;
        }
    }

    private void setOwnerPhoto(String photoName){
        File photo = new File("files/photos/petOwner/"+photoName);
        if(photo != null){
            imgPhoto.setImage(new Image(photo.toURI().toString()));
            imgAddPhoto.setOpacity(0);
        }
    }

    private boolean saveOwner(int petOwnerId){
        PetOwner petOwner = new PetOwner();
        petOwner.setId(petOwnerId);
        petOwner.setFirstName(tfFirstName.getText());
        petOwner.setLastName(tfLastName.getText());
        petOwner.setDob(dpDob.getValue());
        petOwner.setGender(comGender.getValue());
        petOwner.setPhoneNumber(Double.valueOf(tfPhone.getText()));
        petOwner.setAddress(taAddress.getText());
        if(photo != null) {
            String photoName = saveLocalOwnerPhotos(petOwnerId, petOwner.getFirstName());
            PetOwnerDao.insertPetOwnerPhoto(petOwnerId, photoName);
        }
        return PetOwnerDao.savePetOwner(petOwner);
    }


    private String saveLocalOwnerPhotos(int ownerId,String name){
        String extension = getFileExtension(photo.getName()).get();
        String fileName = String.valueOf(ownerId)+name+"."+extension;
        Path copied = Paths.get("files/photos/petOwner/"+fileName);
        Path source = photo.toPath();
        try {
            Files.copy(source,copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private void setPetsName(int petOwnerId){
        ArrayList<String> petsNameFromDb = PetOwnerDao.getPetsName(petOwnerId);
        ObservableList<String> petsNameList = FXCollections.observableArrayList();
        if(!petsNameFromDb.isEmpty()){
            Iterator<String> itr = petsNameFromDb.iterator();
            while(itr.hasNext()){
                petsNameList.add(itr.next());
            }
        }
        petsCol.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(petsNameList));
    }

    private boolean deletePetOwner(int petOwnerId){
        return PetOwnerDao.deletePetOwner(petOwnerId);
    }


    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private void deleteLocalOwnerPhoto(String photoName){
        Path toDelete = Paths.get("files/photos/petOwner/"+photoName);
        try {
            Files.delete(toDelete);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private boolean isFormNotNull(){
        boolean status = true;
        // owner form
        if(tfFirstName.getText().equalsIgnoreCase("")){
            tfFirstName.setPromptText(("Nama depan tidak boleh kosong ! "));
            tfFirstName.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(tfLastName.getText().equalsIgnoreCase("")){
            tfLastName.setPromptText(("Nama Belakang tidak boleh kosong ! "));
            tfLastName.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(dpDob.getValue() == null){
            dpDob.setPromptText(("Tanggal lahir tidak boleh kosong ! "));
            dpDob.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px");
            status = false;
        }
        if(comGender.getValue() == null){
            comGender.setPromptText(("Jenis Kelamin tidak boleh kosong ! "));
            comGender.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);-fx-font-size:16px;");
            status = false;
        }
        if(tfPhone.getText().equalsIgnoreCase("")){
            tfPhone.setPromptText(("Nomer telpon tidak boleh kosong ! "));
            tfPhone.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        if(taAddress.getText().equalsIgnoreCase("")){
            taAddress.setPromptText(("Alamat tidak boleh kosong ! "));
            taAddress.setStyle("-fx-prompt-text-fill: rgba(240,10,10,0.7);");
            status = false;
        }
        return status;
    }


}

class Owners extends RecursiveTreeObject<Owners>{
    StringProperty petOwnerId;
    StringProperty firstName;
    StringProperty lastName;
    StringProperty dob;
    StringProperty gender;
    StringProperty phoneNumber;
    StringProperty address;
    StringProperty pets = new SimpleStringProperty("Klik Untuk Lihat");

    public Owners(String petOwnerId ,String firstName, String lastName, String dob,
                  String gender, String phoneNumber, String address) {
        this.petOwnerId = new SimpleStringProperty(petOwnerId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
    }

}
