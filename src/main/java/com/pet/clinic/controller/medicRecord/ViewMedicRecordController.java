package com.pet.clinic.controller.medicRecord;

import com.jfoenix.controls.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.model.*;
import com.pet.clinic.model.dao.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ViewMedicRecordController {
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
    private Label lblPetWeight;

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
    private JFXTreeTableView<MedicRecords> tvMedicRecord;

    @FXML
    private JFXListView<ActionsData> listActionData;

    @FXML
    private JFXListView<Prescription> listPrescription;

    @FXML
    private Text txtAddress;

    @FXML
    private TextArea taAnamnesis;

    @FXML
    private TextArea taDiagnosis;

    @FXML
    private Label lblVeterinarianId;

    @FXML
    private Label lblVeterinarianName;

    private final ObservableList<Prescription> selectedMedicine = FXCollections.observableArrayList();
    private final ObservableList<ActionsData> selectedActions = FXCollections.observableArrayList();

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
        JFXTreeTableColumn<MedicRecords, String> petWeightCol = new JFXTreeTableColumn<>("Berat Badan");
        petWeightCol.setPrefWidth(120);
        petWeightCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MedicRecords,String> param) ->{
            if(petWeightCol.validateValue(param)) return param.getValue().getValue().petWeight;
            else return null;
        });

        //Add Column To Table
        tvMedicRecord.getColumns().setAll(medicRecordId, recordDateCol,petWeightCol);
        // set medic record table rules
        tvMedicRecord.setShowRoot(false);
        tvMedicRecord.setEditable(false);

        //button Rules
        tfPetId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPetId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // find medicRecord
        tfPetId.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                findPet();
            }
        });
        tfPetId.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearAllData();
            }
        });
        // prescription table

        // clicking on table to get prescription
        tvMedicRecord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tvMedicRecord.getSelectionModel().getSelectedItem() != null){
                    MedicRecords medicRecords = tvMedicRecord.getSelectionModel().getSelectedItem().getValue();
                    loadOtherMedicRecordData(medicRecords);
                    int medicRecordId = Integer.parseInt(medicRecords.medicRecordId.getValue());
                }
            }
        });

        // END OF INITIALIZE
    }

    private void findPet(){
        int petId = 0;
        if (!tfPetId.getText().equalsIgnoreCase("")) {
            petId = Integer.valueOf(tfPetId.getText());
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
                        medicRecord.getRecordDate().toString(),String.valueOf(medicRecord.getVeterinarianId()),
                        medicRecord.getVeterinarianName(),String.valueOf(medicRecord.getPetWeight()),
                        medicRecord.getAnamnesis(),medicRecord.getDiagnosis()));
            }

             /*    private  MedicRecords(String medicRecordId,String recordDate,String veterinarianId,String veterinarianName
                    , String petWeight, String anamnesis,String diagnosis) {*/
        }
        TreeItem<MedicRecords> root = new RecursiveTreeItem<MedicRecords>(medicRecordsList,
                RecursiveTreeObject::getChildren);
        tvMedicRecord.setRoot(root);
    }

    private void loadOtherMedicRecordData(MedicRecords medicRecords){
        taAnamnesis.setText(medicRecords.anamnesis.getValue());
        taDiagnosis.setText(medicRecords.diagnosis.getValue());
        lblVeterinarianId.setText(medicRecords.veterinarianId.getValue());
        lblVeterinarianName.setText(medicRecords.veterinarianName.getValue());
        lblPetWeight.setText(medicRecords.petWeight.getValue());
        setActionsDataList(Integer.parseInt(medicRecords.medicRecordId.getValue()));
        setPrescriptionList(Integer.parseInt(medicRecords.medicRecordId.getValue()));
    }

    private void setPrescriptionList(int medicRecordId){
        selectedMedicine.clear();
        ArrayList<Prescription> prescriptionsFromDb = PrescriptionDao.getPrescription(medicRecordId);
        Iterator<Prescription> itr = prescriptionsFromDb.iterator();
        while(itr.hasNext()){
            selectedMedicine.add(itr.next());
        }
        listPrescription.setItems(selectedMedicine);
    }

    private void setActionsDataList(int medicRecordId){
        selectedActions.clear();
        ArrayList<ActionsData> actionsDataFromDb = ActionsDataDao.getActionsData(medicRecordId);
        Iterator<ActionsData> itr = actionsDataFromDb.iterator();
        while(itr.hasNext()){
            selectedActions.add(itr.next());
        }
        listActionData.setItems(selectedActions);
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
        selectedMedicine.clear();
        selectedActions.clear();
    }
    //END OF MAIN CLASS

    // Additional classes


   public static class MedicRecords extends RecursiveTreeObject<MedicRecords> {
        StringProperty medicRecordId;
        StringProperty recordDate;
        StringProperty veterinarianId;
        StringProperty veterinarianName;
        StringProperty anamnesis;
        StringProperty diagnosis;
        StringProperty petWeight;


     public MedicRecords(String medicRecordId,String recordDate,String veterinarianId,String veterinarianName
             , String petWeight, String anamnesis,String diagnosis) {
            this.medicRecordId = new SimpleStringProperty(medicRecordId);
            this.recordDate = new SimpleStringProperty(recordDate);
            this.petWeight = new SimpleStringProperty(petWeight);
            this.veterinarianId = new SimpleStringProperty(veterinarianId);
            this.veterinarianName = new SimpleStringProperty(veterinarianName);
            this.anamnesis = new SimpleStringProperty(anamnesis);
            this.diagnosis = new SimpleStringProperty(diagnosis);
        }
    }

}

