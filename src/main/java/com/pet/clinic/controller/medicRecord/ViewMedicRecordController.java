package com.pet.clinic.controller.medicRecord;

import com.jfoenix.controls.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.model.MedicRecord;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.Prescription;
import com.pet.clinic.model.dao.MedicRecordDao;
import com.pet.clinic.model.dao.PetDao;
import com.pet.clinic.model.dao.PetOwnerDao;
import com.pet.clinic.model.dao.PrescriptionDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ViewMedicRecordController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfKeyword;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private Label lblSearchStatus;

    @FXML
    private GridPane panePetPhoto;

    @FXML
    private ImageView imgPet;

    @FXML
    private ImageView imgOwner;

    @FXML
    private Label lblPetId;

    @FXML
    private Label lblPetName;

    @FXML
    private Label lblPetDob;

    @FXML
    private Label lblPetKind;

    @FXML
    private Label lblPetGender;

    @FXML
    private Label lblPetRace;

    @FXML
    private Label lblPetColor;

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
/*
    @FXML
    private Label lblOwnerAddress;*/

    @FXML
    private JFXTreeTableView<MedicRecords> tvMedicRecord;

    @FXML
    private JFXTreeTableView<Prescriptions> tvPrescription;

    @FXML
    private JFXListView<String> listActions;

    @FXML
    private Text txtAddress;

    @FXML
    void initialize() {


        //Medic Record columns
        JFXTreeTableColumn<MedicRecords, String> medicRecordId = new JFXTreeTableColumn<>("Id RM");
        medicRecordId.setPrefWidth(80);
        medicRecordId.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, String> param) -> {
            if (medicRecordId.validateValue(param)) return param.getValue().getValue().medicRecordId;
            else return medicRecordId.getComputedValue(param);
        });
        JFXTreeTableColumn<MedicRecords, String> recordDateCol = new JFXTreeTableColumn<>("Tanggal Periksa");
        recordDateCol.setPrefWidth(130);
        recordDateCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, String> param) -> {
            if (recordDateCol.validateValue(param)) return param.getValue().getValue().recordDate;
            else return recordDateCol.getComputedValue(param);
        });
        JFXTreeTableColumn<MedicRecords, String> anamnesisCol = new JFXTreeTableColumn<>("Anamnesis");
        anamnesisCol.setPrefWidth(150);
        anamnesisCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, String> param) -> {
            if (anamnesisCol.validateValue(param)) return param.getValue().getValue().anamnesis;
            else return anamnesisCol.getComputedValue(param);
        });
        JFXTreeTableColumn<MedicRecords, String> diagnosisCol = new JFXTreeTableColumn<>("Diagnosis");
        diagnosisCol.setPrefWidth(150);
        diagnosisCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, String> param) -> {
            if (diagnosisCol.validateValue(param)) return param.getValue().getValue().diagnosis;
            else return diagnosisCol.getComputedValue(param);
        });
        JFXTreeTableColumn<MedicRecords, String> veterinarianCol = new JFXTreeTableColumn<>("Dokter");
        veterinarianCol.setPrefWidth(140);
        veterinarianCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, String> param) -> {
            if (veterinarianCol.validateValue(param)) return param.getValue().getValue().veterinarian;
            else return veterinarianCol.getComputedValue(param);
        });
/*        JFXTreeTableColumn<MedicRecords, TreeItem> actionsCol = new JFXTreeTableColumn<>("Tindakan");
        veterinarianCol.setPrefWidth(120);
        veterinarianCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords, TreeItem> param) ->{
            if(actionsCol.validateValue(param)) return param.getValue().getValue().actions.getValue();
            else return null;
        });*/

        //Add Column To Table
        tvMedicRecord.getColumns().setAll(medicRecordId, recordDateCol, anamnesisCol, diagnosisCol, veterinarianCol);

        // set medic record table rules
        tvMedicRecord.setShowRoot(false);
        tvMedicRecord.setEditable(false);

        //button Rules
        tfKeyword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfKeyword.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // find medicRecord
        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                findPet();
            }
        });
        tfKeyword.setOnAction(event -> {
            findPet();
        });
        tfKeyword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearAllData();
            }
        });


        // prescription table
        // prepare columns
        JFXTreeTableColumn<Prescriptions, String> medicineNameCol = new JFXTreeTableColumn<>("Nama Obat");
        medicineNameCol.setPrefWidth(120);
        medicineNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Prescriptions, String> param) ->{
            if(medicineNameCol.validateValue(param)) return param.getValue().getValue().medicineName;
            else return medicineNameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Prescriptions, String> descriptionCol = new JFXTreeTableColumn<>("Keterangan");
        descriptionCol.setPrefWidth(200);
        descriptionCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Prescriptions, String> param) ->{
            if(descriptionCol.validateValue(param)) return param.getValue().getValue().description;
            else return descriptionCol.getComputedValue(param);
        });

        // add columns to prescription table
        tvPrescription.getColumns().addAll(medicineNameCol,descriptionCol);

        //set prescription table rules
        tvPrescription.setShowRoot(false);
        tvPrescription.setEditable(false);

        // clicking on table to get prescription
        tvMedicRecord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tvMedicRecord.getSelectionModel().getSelectedItem() != null){
                    MedicRecords medicRecord = tvMedicRecord.getSelectionModel().getSelectedItem().getValue();
                    int medicRecordId = Integer.valueOf(medicRecord.medicRecordId.getValue().toString());
                    loadPrescriptionTable(medicRecordId);
                    setListActions(medicRecordId);
                }
            }
        });

        // END OF INITIALIZE
    }
/*
    private boolean setMedicRecord(int petId){
        boolean status = false;
        setPet(petId)
        return status;
    }*/

    private void findPet(){
        int petId = 0;
        if (!tfKeyword.getText().equalsIgnoreCase("")) {
            petId = Integer.valueOf(tfKeyword.getText());
            int ownerId = setPet(petId);
            if (ownerId != 0) {
                boolean isOwner = setOwner(ownerId);
                if (isOwner) {
                    lblSearchStatus.setVisible(false);
                    loadMedicRecordData(petId);
                }
            } else {
                lblSearchStatus.setText("Peliharaan Tidak di Temukan");
                lblSearchStatus.setVisible(true);
            }
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
            lblPetKind.setText(pet.getKind());
            lblPetRace.setText(pet.getRace());
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
    //        lblOwnerAddress.setText(petOwner.getAddress());
            txtAddress.setText(petOwner.getAddress());
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

    // Medic Record load Data
    private void loadMedicRecordData(int petId) {
        ArrayList<MedicRecord> medicRecordsFromDb = MedicRecordDao.getMedicRecord(petId);
        ObservableList<MedicRecords> medicRecordsList = FXCollections.observableArrayList();
        if (!medicRecordsFromDb.isEmpty()) {
            Iterator<MedicRecord> itr = medicRecordsFromDb.iterator();
            while (itr.hasNext()) {
                MedicRecord medicRecord = itr.next();
                medicRecordsList.add(new MedicRecords(String.valueOf(medicRecord.getMedicRecordId()),
                        medicRecord.getRecordDate().toString(), medicRecord.getAnamnesis(),
                        medicRecord.getDiagnosis(),
                        medicRecord.getVeterinarian()));
            }
        }
        TreeItem<MedicRecords> root = new RecursiveTreeItem<MedicRecords>(medicRecordsList,
                RecursiveTreeObject::getChildren);
        tvMedicRecord.setRoot(root);
    }

    private void loadPrescriptionTable(int medicRecordId){
        ArrayList<Prescription> prescriptionsFromDb = PrescriptionDao.getPrescription(medicRecordId);
        ObservableList<Prescriptions> prescriptionsList = FXCollections.observableArrayList();
        if(!prescriptionsFromDb.isEmpty()){
            Iterator<Prescription> itr = prescriptionsFromDb.iterator();
            while (itr.hasNext()){
                Prescription prescription = itr.next();
                prescriptionsList.add(new Prescriptions(prescription.getMedicineName(),prescription.getDescription()));
            }
        }
        TreeItem<Prescriptions> root = new RecursiveTreeItem<Prescriptions>(prescriptionsList,
                RecursiveTreeObject::getChildren);
        tvPrescription.setRoot(root);
    }

    private  void setListActions(int medicRecordId){
        ArrayList<String> actions = MedicRecordDao.getActionsName(medicRecordId);
        Iterator<String> itr = actions.iterator();
        listActions.getItems().clear();
        while (itr.hasNext()){
            listActions.getItems().add(itr.next());
        }
    }

    private void clearAllData(){
        //clear owner labels
        lblOwnerId.setText("ID");
        lblOwnerName.setText("Nama");
        lblOwnerGender.setText("Jenis Kelamin");
        lblOwnerDob.setText("Tanggal Lahir");
        lblOwnerPhone.setText("Nomer Telpon");
        txtAddress.setText("Alamat");
        imgOwner.setImage(null);

        //clear pet labales
        lblPetId.setText("ID");
        lblPetName.setText("Nama");
        lblPetDob.setText("Tanggal Lahir");
        lblPetKind.setText("Jenis");
        lblPetGender.setText("Jenis Kelamin");
        lblPetRace.setText("Ras");
        lblPetColor.setText("Warna");
        imgPet.setImage(null);

        //clear medic record
        tvMedicRecord.setRoot(null);
        tvPrescription.setRoot(null);
        listActions.getItems().clear();
    }
    //END OF MAIN CLASS
}

class MedicRecords extends RecursiveTreeObject<MedicRecords> {
    StringProperty petId;
    StringProperty medicRecordId;
    StringProperty recordDate;
    StringProperty veterinarian;
    StringProperty anamnesis;
    StringProperty diagnosis;

    public MedicRecords(String medicRecordId,String recordDate, String anamnesis,
                        String diagnosis, String veterinarian) {
        this.medicRecordId = new SimpleStringProperty(medicRecordId);
        this.recordDate = new SimpleStringProperty(recordDate);
        this.anamnesis = new SimpleStringProperty(anamnesis);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.veterinarian = new SimpleStringProperty(veterinarian);
    }
}

class Prescriptions extends  RecursiveTreeObject<Prescriptions>{
/*    StringProperty medicRecordId;
    StringProperty medicineId;*/
//    StringProperty veterinarianId;
    StringProperty medicineName;
    StringProperty description;

    public Prescriptions(String medicineName, String description) {
        this.medicineName = new SimpleStringProperty(medicineName);
        this.description = new SimpleStringProperty(description);
    }
}
