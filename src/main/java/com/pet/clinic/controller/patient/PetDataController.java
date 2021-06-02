package com.pet.clinic.controller.patient;

import com.jfoenix.controls.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.dao.PetDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

public class PetDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private Label lblId;

    @FXML
    private Label lblOwnerId;

    @FXML
    private JFXButton btnSave;

    @FXML
    private GridPane panePetPhoto;

    @FXML
    private ImageView imgPet;

    @FXML
    private JFXButton btnPetPhoto;

    @FXML
    private TextField tfPetName;

    @FXML
    private DateTimePicker dpPetDOB;

    @FXML
    private JFXComboBox<String> comPetKind;

    @FXML
    private JFXComboBox<String> comPetGender;

    @FXML
    private TextField tfPetRace;

    @FXML
    private TextField tfPetColor;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView imgAddPetPhoto;

    @FXML
    private JFXTreeTableView<Pets> tableView;

    @FXML
    private HBox boxBtns;

    private File petPhoto;

    @FXML
    void initialize() {
        //Craeate table column
        JFXTreeTableColumn<Pets, String> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setPrefWidth(80);
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(idCol.validateValue(param)) return param.getValue().getValue().id;
            else return idCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> ownerIdCol = new JFXTreeTableColumn<>("ID Pemilik");
        ownerIdCol.setPrefWidth(80);
        ownerIdCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(ownerIdCol.validateValue(param)) return param.getValue().getValue().ownerId;
            else return ownerIdCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> nameCol = new JFXTreeTableColumn<>("Nama");
       nameCol.setPrefWidth(100);
        nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(nameCol.validateValue(param)) return param.getValue().getValue().name;
            else return nameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> dobCol = new JFXTreeTableColumn<>("Tanggal Lahir");
        dobCol.setPrefWidth(110);
        dobCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(dobCol.validateValue(param)) return param.getValue().getValue().dob;
            else return dobCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> genderCol = new JFXTreeTableColumn<>("Jenis Kelamin");
        genderCol.setPrefWidth(110);
        genderCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(genderCol.validateValue(param)) return param.getValue().getValue().gender;
            else return genderCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> kindCol = new JFXTreeTableColumn<>("Jenis");
        kindCol.setPrefWidth(100);
        kindCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(kindCol.validateValue(param)) return param.getValue().getValue().kind;
            else return kindCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> raceCol = new JFXTreeTableColumn<>("Ras");
        raceCol.setPrefWidth(100);
        raceCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(raceCol.validateValue(param)) return param.getValue().getValue().race;
            else return raceCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> colorCol = new JFXTreeTableColumn<>("Warna");
        colorCol.setPrefWidth(100);
        colorCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(colorCol.validateValue(param)) return param.getValue().getValue().color;
            else return colorCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Pets, String> timestampCol = new JFXTreeTableColumn<>("Tanggal Terdaftar");
        timestampCol.setPrefWidth(175);
        timestampCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(timestampCol.validateValue(param)) return param.getValue().getValue().timestamp;
            else return timestampCol.getComputedValue(param);
        });

        //Add Column To Table
        tableView.getColumns().setAll(idCol,ownerIdCol,nameCol,dobCol,genderCol,kindCol,raceCol,colorCol,timestampCol);

        // Load Table data
        loadTableData();

        //Set Table Rules;
        tableView.setShowRoot(false);
        tableView.setEditable(false);


        //Click table row to get Data
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tableView.getSelectionModel().getSelectedItem() != null){
                    Pets pet = tableView.getSelectionModel().getSelectedItem().getValue();
                    int id = Integer.valueOf(pet.id.getValue().toString());
                   getPetDetail(id);
                }
            }
        });

        // set pet gender
        comPetGender.getItems().add("Jantan");
        comPetGender.getItems().add("Betina");

        // set pet kind
        comPetKind.getItems().add("Kucing");
        comPetKind.getItems().add("Anjing");
        comPetKind.getItems().add("Hamster");

        //photo chooser
        FileChooser choosePhoto = new FileChooser();
        choosePhoto.setTitle("Piih Foto");
        choosePhoto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","*.png"));

        imgAddPetPhoto.setOpacity(0);
        btnPetPhoto.setOpacity(0);
        btnPetPhoto.setOnAction(e ->{
            petPhoto = choosePhoto.showOpenDialog(new Stage());
            if(petPhoto != null) {
                imgPet.setImage(new Image(petPhoto.toURI().toString()));
            }
        });
        btnPetPhoto.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imgAddPetPhoto.setOpacity(0.5);
                btnPetPhoto.setOpacity(1);
            }
        });

        btnPetPhoto.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imgAddPetPhoto.setOpacity(0);
                btnPetPhoto.setOpacity(0);
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
                savePet();
                loadTableData();
            }
        });
        btnDelete.setOnAction(e->{
           deletePet(Integer.valueOf(lblId.getText()));
           loadTableData();
        });


    }

    private void loadTableData(){
        ArrayList<Pet> petsFromDb = PetDao.getAllPet();
        ObservableList<Pets> petsList = FXCollections.observableArrayList();
        Iterator<Pet> itr = petsFromDb.iterator();
        while(itr.hasNext()){
            Pet pet = itr.next();
            petsList.add(new Pets(
                    String.valueOf(pet.getId()),String.valueOf(pet.getOwnerId()),pet.getName(),
                    String.valueOf(pet.getDob()),pet.getGender(),pet.getKind(),
                    pet.getRace(),pet.getColor(), String.valueOf(pet.getTimestamp())
            ));
        }
        TreeItem<Pets> root = new RecursiveTreeItem<Pets>(petsList, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
    }

    private void setEditableForm(boolean isEditable , double opacity){
        btnPetPhoto.setVisible(isEditable);
        tfPetColor.setEditable(isEditable);
        tfPetRace.setEditable(isEditable);
        tfPetName.setEditable(isEditable);
        comPetKind.setDisable(!isEditable);
        comPetGender.setDisable(!isEditable);
        dpPetDOB.setEditable(isEditable);
        tfPetColor.setOpacity(opacity);
        tfPetRace.setOpacity(opacity);
        tfPetName.setOpacity(opacity);
        dpPetDOB.setOpacity(opacity);
    }


    private void getPetDetail(int id){
        Pet pet = PetDao.getPet(id);
        lblId.setText(String.valueOf(pet.getId()));
        lblOwnerId.setText(String.valueOf(pet.getOwnerId()));
        tfPetName.setText(pet.getName());
        dpPetDOB.setDateTimeValue(pet.getDob().atTime(00,00,00));
        comPetGender.setValue(pet.getGender());
        comPetKind.setValue(pet.getKind());
        tfPetRace.setText(pet.getRace());
        tfPetColor.setText(pet.getColor());
        setPetPhoto(pet.getPhoto());
    }

    private void deletePet(int id){
        Pet pet = PetDao.getPet(id);
        PetDao.deletePet(pet.getId());
        deleteLocalPetPhoto(pet.getPhoto());

    }

    private void setPetPhoto(String photoName){
        File photo = new File("files/photos/pet/"+photoName);
        if(photo != null){
            imgPet.setImage(new Image(photo.toURI().toString()));
        }
    }

    private boolean savePet(){
        boolean status = false;
        Pet pet = new Pet();
        pet.setId(Integer.valueOf(lblId.getText()));
        pet.setOwnerId(Integer.valueOf(lblOwnerId.getText()));
        pet.setOwnerId(Integer.valueOf(lblOwnerId.getText()));
        pet.setName(tfPetName.getText());
        pet.setDob(dpPetDOB.getValue());
        pet.setGender(comPetGender.getValue());
        pet.setKind(comPetKind.getValue());
        pet.setRace(tfPetRace.getText());
        pet.setColor(tfPetColor.getText());
        status = PetDao.updatePet(pet);
        if(petPhoto != null)
        saveLocalPetPhoto(pet.getId(),pet.getName());
        return status;
    };

    private void saveLocalPetPhoto(int petId, String name){
        String extension = getFileExtension(petPhoto.getName()).get();
        String fileName = String.valueOf(petId)+name+"."+extension;
        Path copied = Paths.get("files/photos/pet/"+fileName);
        Path source = petPhoto.toPath();
        try {
            Files.copy(source,copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteLocalPetPhoto(String photoName){
        Path toDelete = Paths.get("files/photos/pet/"+photoName);
        try {
            Files.delete(toDelete);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
class Pets extends RecursiveTreeObject<Pets> {
    StringProperty id;
    StringProperty ownerId;
    StringProperty name;
    StringProperty dob;
    StringProperty gender;
    StringProperty kind;
    StringProperty race;
    StringProperty color;
    StringProperty timestamp;

    public Pets(String id, String ownerId, String name,String dob,String gender,String kind,String race,
                String color, String timeStamp) {
        this.id = new SimpleStringProperty(id) ;
        this.ownerId = new SimpleStringProperty(ownerId) ;
        this.name = new SimpleStringProperty(name) ;
        this.dob = new SimpleStringProperty(dob) ;
        this.gender = new SimpleStringProperty(gender) ;
        this.kind = new SimpleStringProperty(kind) ;
        this.race = new SimpleStringProperty(race) ;
        this.color = new SimpleStringProperty(color) ;
        this.timestamp = new SimpleStringProperty(timeStamp) ;

    }
}
