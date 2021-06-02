package com.pet.clinic.controller.patient;

import com.jfoenix.controls.*;

import java.net.HttpRetryException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tornadofx.control.DateTimePicker;

public class PetDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private GridPane panePetPhoto;

    @FXML
    private ImageView imgPet;

    @FXML
    private ImageView imgAddPetPhoto;

    @FXML
    private JFXButton btnPetPhoto;

    @FXML
    private TextField tfPetName;

    @FXML
    private DateTimePicker dpPetDOB;

    @FXML
    private JFXComboBox<?> comPetKind;

    @FXML
    private JFXComboBox<?> comPetGender;

    @FXML
    private TextField tfPetRace;

    @FXML
    private TextField tfPetColor;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView<Pets> tableView;

    @FXML
    void initialize() {
        //Add tabel column
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
        timestampCol.setPrefWidth(120);
        timestampCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pets, String> param) ->{
            if(timestampCol.validateValue(param)) return param.getValue().getValue().timestamp;
            else return timestampCol.getComputedValue(param);
        });

        // Table Data
        ObservableList<Pets> petsList = FXCollections.observableArrayList();
        petsList.add(new Pets("1000","2000","Elo","02-04-2017","Jantab",
                "Kucing","Domestik","Putih","01-06-2021"));

        //build tree
        TreeItem<Pets> root = new RecursiveTreeItem<Pets>(petsList, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.setEditable(false);
        tableView.getColumns().setAll(idCol,ownerIdCol,nameCol,dobCol,genderCol,kindCol,raceCol,colorCol,timestampCol);
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
