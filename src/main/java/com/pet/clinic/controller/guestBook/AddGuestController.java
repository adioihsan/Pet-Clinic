package com.pet.clinic.controller.guestBook;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.Guest;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.dao.GuestDao;
import com.pet.clinic.model.dao.PetDao;
import com.pet.clinic.model.dao.PetOwnerDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
    private JFXButton btnAdd;

    @FXML
    private Label lblSearchStatus;

    @FXML
    private ImageView imgPet;

    @FXML
    private Label lblPetId;

    @FXML
    private Label lblPetName;

    @FXML
    private Label lblPetKind;

    @FXML
    private Label lblPetGender;

    @FXML
    private Label lblPetDob;

    @FXML
    private Label lblPetColor;

    @FXML
    private ImageView imgOwner;

    @FXML
    private Label lblOwnerId;

    @FXML
    private Label lblOwnerName;

    @FXML
    private Label lblOwnerGender;

    @FXML
    private Label lblOwnerDob;

    @FXML
    private Label lblOwnerPhone;

    @FXML
    private TableView<Guest> tblGuest;

    @FXML
    private TableColumn<Guest, LocalDateTime> colVisitDate;

    @FXML
    private TableColumn<Guest, Integer> colPetOwnerId;

    @FXML
    private TableColumn<Guest, String> colPetOwnerName;

    @FXML
    private TableColumn<Guest, Integer> colPetId;

    @FXML
    private TextField tfLimit;

    @FXML
    private JFXButton btnAddLimit;

    @FXML
    private TextField tfFind;

    @FXML
    private TableColumn<Guest, String> colPetName;
    private  final ObservableList<Guest> guestList = FXCollections.observableArrayList();
    private  boolean isFound = false;

    @FXML
    void initialize() {
        // field rules
        tfPetId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPetId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        // on type
        tfPetId.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!tfPetId.getText().equalsIgnoreCase("")) {
                   int  petId = Integer.valueOf(tfPetId.getText());
                    findPet(petId);
                }
            }
        });
        //table guest column
        loadGuest(Integer.parseInt(tfLimit.getText()));
        tblGuest.setItems(guestList);
        colPetId.setCellValueFactory(new PropertyValueFactory<Guest,Integer>("petId"));
        colPetName.setCellValueFactory(new PropertyValueFactory<Guest,String>("petName"));
        colPetOwnerId.setCellValueFactory(new PropertyValueFactory<Guest,Integer>("petOwnerId"));
        colPetOwnerName.setCellValueFactory(new PropertyValueFactory<Guest,String>("petOwnerName"));
        colVisitDate.setCellValueFactory(new PropertyValueFactory<Guest,LocalDateTime>("visitTime"));

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isFound){
                    if(saveGuest(Integer.parseInt(tfPetId.getText()))){
                        Message.showSuccess("Tamu Berhasil di Tambahkan");
                        loadGuest(Integer.parseInt(tfLimit.getText()));
                    }
                    else
                        Message.showFailed("Tamu Gagal di Tambahkan");
                }
            }
        });
        tblGuest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 && tblGuest.getSelectionModel().getSelectedItem() != null){
                    Guest guest = tblGuest.getSelectionModel().getSelectedItem();
                    if(ConfirmationDialog.showMakeSure("Hapus Tamu dengan ID "+guest.getPetId())){
                        deleteGuest(guest.getPetId(),guest.getVisitTime());
                        loadGuest(Integer.parseInt(tfLimit.getText()));
                    };
                }
                else if(event.getClickCount() == 1 && tblGuest.getSelectionModel().getSelectedItem() != null){
                    Guest guest = tblGuest.getSelectionModel().getSelectedItem();
                    findPet(guest.getPetId());
                }
            }
        });

        tfFind.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int limit = Integer.parseInt(tfLimit.getText());
                loadGuest(tfFind.getText() ,limit);
                if(tfFind.getText().equalsIgnoreCase(""))
                    loadGuest(limit);
            }
        });
        // field rules
        tfLimit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfLimit.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        tfLimit.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(tfLimit.getText().equalsIgnoreCase(""))
                    tfLimit.setText(String.valueOf(1));
                loadGuest(Integer.parseInt(tfLimit.getText()));
            }
        });
        btnAddLimit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tfLimit.setText(String.valueOf(Integer.parseInt(tfLimit.getText())+1));
                loadGuest(Integer.parseInt(tfLimit.getText()));
            }
        });
    }


    private void findPet(int petId){
            int ownerId = setPet(petId);
            if (ownerId != 0) {
                boolean isOwner = setOwner(ownerId);
                if (isOwner) {
                    lblSearchStatus.setVisible(false);
                    isFound = true;
                }
            } else {
                lblSearchStatus.setText("Peliharaan Tidak di Temukan");
                lblSearchStatus.setVisible(true);
                clearAllData();
                isFound = false;
            }
    }

    private int setPet(int petId) {
        int ownerId = 0;
        Pet pet = PetDao.getPet(petId);
        if (pet != null) {
            lblPetId.setText(String.valueOf(pet.getId()));
            lblOwnerId.setText(String.valueOf(pet.getOwnerId()));
            lblPetName.setText(pet.getName());
            lblPetDob.setText(pet.getDob().toString());
            lblPetGender.setText(pet.getGender());
            lblPetKind.setText(pet.getKind() + "/" +pet.getRace());
            lblPetColor.setText(pet.getColor());
            setPetPhoto(pet.getPhoto());
            ownerId = pet.getOwnerId();
        }
        return ownerId;
    }

    private boolean setOwner(int ownerId) {
        PetOwner petOwner = PetOwnerDao.getOwner(ownerId);
        if (petOwner != null) {

            lblOwnerName.setText(petOwner.getFirstName() + " " + petOwner.getLastName());
            lblOwnerDob.setText(petOwner.getDob().toString());
            lblOwnerGender.setText(petOwner.getGender());
            lblOwnerPhone.setText(String.format("%.0f",petOwner.getPhoneNumber()));
            setOwnerPhoto(petOwner.getPhoto());
            return true;
        } else {
            return false;
        }
    }

    private void setPetPhoto(String photoName) {
        File photo = new File("files/photos/pet/" + photoName);
        if (photo != null) {
            imgPet.setImage(new Image(photo.toURI().toString()));
        }
    }

    private void setOwnerPhoto(String photoName) {
        File photo = new File("files/photos/petOwner/" + photoName);
        if (photo != null) {
            imgOwner.setImage(new Image(photo.toURI().toString()));
        }
    }

    private  void loadGuest(int limit){
        ArrayList<Guest> listFromDb = GuestDao.getAllGuest(limit);
        guestList.setAll(listFromDb);
    }
    private  void loadGuest(String keyword,int limit){
        ArrayList<Guest> listFromDb = GuestDao.findGuest(keyword,limit);
        guestList.setAll(listFromDb);
    }

    private  boolean saveGuest(int petId){
        return GuestDao.insertGuest(petId, Timestamp.from(Instant.now()));
    }

    private boolean deleteGuest(int petId,LocalDateTime localDateTime){
        return GuestDao.deleteGuest(petId,Timestamp.valueOf(localDateTime));
    }


    private void clearAllData(){
        //clear owner labels
        lblOwnerId.setText("ID");
        lblOwnerName.setText("Nama");
        lblOwnerGender.setText("Jenis Kelamin");
        lblOwnerDob.setText("Tanggal Lahir");
        lblOwnerPhone.setText("Nomer Telpon");
        imgOwner.setImage(null);

        //clear pet labales
        lblPetId.setText("ID");
        lblPetName.setText("Nama");
        lblPetDob.setText("Tanggal Lahir");
        lblPetKind.setText("Jenis");
        lblPetGender.setText("Jenis Kelamin");
        lblPetColor.setText("Warna");
        imgPet.setImage(null);

    }
}
