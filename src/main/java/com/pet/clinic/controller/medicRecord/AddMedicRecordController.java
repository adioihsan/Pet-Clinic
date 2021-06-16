package com.pet.clinic.controller.medicRecord;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import com.pet.clinic.helper.Popup;
import com.pet.clinic.model.*;
import com.pet.clinic.model.dao.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddMedicRecordController {
    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfPetId;

    @FXML
    private Label lblSearchStatus;

    @FXML
    private TextField tfVeterinarianId;

    @FXML
    private TextField tfPetWeight;

    @FXML
    private TextArea taAnamnesis;

    @FXML
    private TextArea taDiagnosis;

    @FXML
    private TextField tfFindAction;

    @FXML
    private TableView<Action> tblAction;


    @FXML
    private TableColumn<Action, Integer> colActionId;

    @FXML
    private TableColumn<Action, String > colActionName;

    @FXML
    private TextField tfFindMedicine;

    @FXML
    private TableView<Medicine> tblMedicine;

    @FXML
    private TableColumn<Medicine, Integer> colMedicineId;

    @FXML
    private TableColumn<Medicine, String> colMedicineName;

    @FXML
    private GridPane panePetPhoto;

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
    private Label lblPetWeight;

    @FXML
    private Label lblVeterinarianId;

    @FXML
    private Label lblVeterinarianName;

    @FXML
    private Label lblOwnerId;

    @FXML
    private Label lblOwnerName;

    @FXML
    private Label lblVetSearchStatus;

    @FXML
    private JFXListView<ActionsData> listActionData;

    @FXML
    private JFXListView<Prescription> listPrescription;

    @FXML
    private JFXButton btnSave;

    private final  ObservableList<Prescription> selectedMedicine = FXCollections.observableArrayList();
    private final ObservableList<ActionsData> selectedActions = FXCollections.observableArrayList();
    @FXML
    void initialize() {

        // some field rules

        // set tf number only rules
        tfVeterinarianId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfVeterinarianId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tfPetId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPetId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        tfPetWeight.setTextFormatter(formatter);

        //End of some field rules

      // find pet , owner based on pet id
     tfPetId.setOnKeyTyped(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent keyEvent) {
          findPet();
        }
    });
        // end of find pet

        //find veterinarian based on vet id
        tfVeterinarianId.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!tfVeterinarianId.getText().equalsIgnoreCase(""))
                setVetDetail(Integer.parseInt(tfVeterinarianId.getText()));
            }
        });

        //end of find veterinarian based on vet id

        // Medicine Table column
        colMedicineId.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("id"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("name"));
        // Add action button for every column value

        // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.



        //find medicines
        tfFindMedicine.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                findMedicine(tfFindMedicine.getText(),10);
            }
        });

        // add medicine to selected
        tblMedicine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tblMedicine.getSelectionModel().getSelectedItem() != null){
                    Medicine medicine = tblMedicine.getSelectionModel().getSelectedItem();
                    createPrescription(medicine);
                }
            }
        });
       // listMedicine.setItems(selectedMedicines);

        //delete selected medicine
        listPrescription.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(listPrescription.getSelectionModel().getSelectedItem() != null){
                    Prescription prescription = listPrescription.getSelectionModel().getSelectedItem();
                    boolean isDelete = ConfirmationDialog.showDelete("Hapus Obat "
                            +prescription.getMedicineName()+" ?");
                    selectedMedicine.remove(prescription);
                }
            }
        });
        listPrescription.setItems(selectedMedicine);

        // set table action column
        colActionId.setCellValueFactory(new PropertyValueFactory<Action,Integer>("actionId"));
        colActionName.setCellValueFactory(new PropertyValueFactory<Action,String>("name"));

        //find action
        tfFindAction.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                findAction(tfFindAction.getText());
            }
        });
        // change action into actionsData when table clikced
        tblAction.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(tblAction.getSelectionModel().getSelectedItem() != null){
                    Action action = tblAction.getSelectionModel().getSelectedItem();
                    createActionsData(action);
                }
            }
        });
        listActionData.setItems(selectedActions);
        //remove action when list clikced
        listActionData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(listActionData.getSelectionModel().getSelectedItem() != null){
                    ActionsData actionsData = listActionData.getSelectionModel().getSelectedItem();
                    boolean isDelete = ConfirmationDialog.showDelete("Hapus Tindakan "+
                            actionsData.getActionName() +" ?");
                    if(isDelete)selectedActions.remove(actionsData);
                }
            }
        });

        //end of actions bla bla

        // Now the save button hahahha
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isFormNotNull()) {
                    boolean isOK = saveMedicRecord();
                    if (isOK) {
                        clearForm();
                        Message.showSuccess("Data Rekam Medis Berhasil di Simpan");
                    } else {
                        Message.showFailed("Terjadi Kesalahan. Data Rekam Medis Gagal di Simpan");
                    }
                }
            }
        });


        // END OF INITIALIZE
    }

    // set pet,owner,doctor information
    private void findPet(){
        int petId = 0;
        if (!tfPetId.getText().equalsIgnoreCase("")) {
            petId = Integer.valueOf(tfPetId.getText());
            int ownerId = setPet(petId);
            if (ownerId != 0) {
                boolean isOwner = setOwner(ownerId);
                if (isOwner) {
                    lblSearchStatus.setVisible(false);
                }
            } else {
                lblSearchStatus.setText("Peliharaan Tidak di Temukan");
                lblSearchStatus.setVisible(true);
                clearPetData();
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
            lblPetKind.setText(pet.getKind() +"/"+ pet.getRace());
            lblPetGender.setText(pet.getGender());
            lblPetDob.setText(pet.getDob().toString());
            lblPetWeight.setText(String.valueOf(PetDao.getPetWeight(pet.getId()))+" Kg");
            setPetPhoto(pet.getPhoto());
            ownerId = pet.getOwnerId();
        }
        return ownerId;
    }

    private boolean setOwner(int ownerId) {
        PetOwner petOwner = PetOwnerDao.getOwner(ownerId);
        if (petOwner != null) {
            lblOwnerId.setText(String.valueOf(petOwner.getId()));
            lblOwnerName.setText(petOwner.getFirstName() + " " + petOwner.getLastName());
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

    private void setVetDetail(int id){
        Veterinarian vet = VeterinarianDao.getVeterinarian(id);
        if(vet != null) {
            lblVeterinarianId.setText(String.valueOf(vet.getId()));
            lblVeterinarianName.setText(vet.getFirstName() + " " + vet.getLastName());
            lblVetSearchStatus.setVisible(false);
        }
        else{
            lblVetSearchStatus.setText("Dokter Tidak di Temukan");
            lblVetSearchStatus.setVisible(true);
           clearVeterinarianData();
        }

    }
    //end of set pet,owner,doctor

    // find medicine based on name

    private void findMedicine(String keyword, int limit){
        ArrayList<Medicine> medicinesFromDb = MedicineDao.findMedicine(keyword , limit);
        ObservableList<Medicine> medicines = FXCollections.observableArrayList(medicinesFromDb);
        tblMedicine.getColumns().setAll(colMedicineId,colMedicineName);
        tblMedicine.setItems(medicines);
    }
    // change medicine into prescription then show it into medicine list;

    private void createPrescription(Medicine medicine){

        //set value to popup
        if(medicine.getStock() != 0) {
            Popup popup = new Popup();
            PrescriptionPopupController pspc;
            String medicineName = "(" + medicine.getId() + ") " + medicine.getName() + " | " + medicine.getFill();
            pspc = (PrescriptionPopupController) popup.load(listPrescription.getScene().getWindow(),
                    "medicRecord/prescriptionPopup");
            pspc.setMedicineName(medicineName);
            pspc.setUnit(medicine.getUnit());
            pspc.setStock(medicine.getStock());

            //get value from popup
            final Prescription[] prescription = {null};
            JFXButton btnOK = pspc.getBtnOK();
            btnOK.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!pspc.getTfAmount().getText().equalsIgnoreCase("") &&
                            !pspc.getTfDescription().getText().equalsIgnoreCase("")) {
                        prescription[0] = new Prescription();
                        prescription[0].setMedicineId(medicine.getId());
                        prescription[0].setAmount(pspc.getAmount());
                        prescription[0].setDescription(pspc.getDescription());
                        prescription[0].setMedicineName(medicine.getName() + " (" + medicine.getFill() + ") ");
                        prescription[0].setUnit(medicine.getUnit());
                        // prescription.setMedicRecordId();
                        Stage stage = (Stage) btnOK.getScene().getWindow();
                        stage.close();
                        selectedMedicine.add(prescription[0]);
                    } else {
                        pspc.getTfAmount().setPromptText("Jumlah obet tidak boleh kosong !");
                        pspc.getTfDescription().setPromptText("Keterangan Tidak boleh Kosong !");
                    }
                }
            });
        }
        else{
            Message.showFailed("Stock Obat Habis !");
        }
    }
    /// end change medicine into prescription then show it into medicine list;

    // find actions based on keyword

    private void findAction(String keyword){
        ArrayList<Action> actionsFromDb = ActionDao.findAction(keyword,10);
        ObservableList<Action> actionsList = FXCollections.observableArrayList(actionsFromDb);
        tblAction.setItems(actionsList);
    }

    private void createActionsData(Action action){
        final ActionsData[] actionsData = {null};
        //set value to popup
        Popup popup = new Popup();
        ActionPopupController apc = (ActionPopupController) popup.load(listActionData.getScene().getWindow(),
                "medicRecord/actionPopup");
        apc.setActionName("("+action.getActionId()+") "+action.getName());

        //get value from popup
        JFXButton btnOk = apc.getBtnOk();
        btnOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                actionsData[0] = new ActionsData();
                actionsData[0].setActionId(action.getActionId());
                actionsData[0].setActionName(action.getName());
                actionsData[0].setDescription(apc.getDescription());
                selectedActions.add(actionsData[0]);
                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close();
            }
        });
    }

    // Save medic record
        private boolean saveMedicRecord(){
        boolean status = false;
            int medicRecordId = saveMedicRecordMainData();
            if(medicRecordId != 0){
                status = saveActionsData(medicRecordId) && savePrescription(medicRecordId);
            }
            return status;
        }
        private int saveMedicRecordMainData(){
        MedicRecord medicRecord = new MedicRecord();
        medicRecord.setAnamnesis(taAnamnesis.getText());
        medicRecord.setDiagnosis(taDiagnosis.getText());
        medicRecord.setPetId(Integer.valueOf(lblPetId.getText()));
        medicRecord.setVeterinarianId(Integer.valueOf(lblVeterinarianId.getText()));
        medicRecord.setRecordDate(LocalDate.now());
        medicRecord.setPetWeight(Double.valueOf(tfPetWeight.getText()));
        return  MedicRecordDao.saveMedicRecord(medicRecord);

        }
        private boolean saveActionsData(int medicRecordId){
            boolean status = true;
            ListIterator<ActionsData> itr = selectedActions.listIterator();
            while(itr.hasNext()){
                ActionsData actionsData = itr.next();
                actionsData.setMedicRecordId(medicRecordId);
                if(!ActionsDataDao.saveActionsData(actionsData)){
                    status = false;
                };
            }
            return status;
        }
        private boolean savePrescription(int medicRecordId){
        boolean status = true;
        ListIterator<Prescription> itr = selectedMedicine.listIterator();
        while(itr.hasNext()){
            Prescription prescription = itr.next();
            prescription.setMedicRecordId(medicRecordId);
            if(!PrescriptionDao.insertPrescription(prescription)) {
                status = false;
            };
        }
        return status;
        }

    // chcek form before submit

    private boolean isFormNotNull(){
        boolean status = true;
        if(lblVeterinarianId.getText().equalsIgnoreCase("ID Dokter")){
            lblVeterinarianId.setText("Pilih Dokter !");
            lblVeterinarianId.setStyle("-fx-text-fill:red;");
            tfVeterinarianId.setPromptText("Masukan ID Dokter Disini ! ");
            tfVeterinarianId.setStyle("-fx-prompt-text-fill:red");
            status = false;
        }
        if(lblPetId.getText().trim().equalsIgnoreCase("ID")){
            lblPetId.setText("Pilih Peliharaan !");
            lblPetId.setStyle("-fx-text-fill:red;");
            tfPetId.setPromptText("Masukan ID Peliharaan Disini ! ");
            tfPetId.setStyle("-fx-prompt-text-fill:red");
            status = false;
        }
        if(tfPetWeight.getText().equalsIgnoreCase("")){
            tfPetWeight.setPromptText("Berat Badan Tidak Boleh Kosong ! ");
            tfPetWeight.setStyle("-fx-prompt-text-fill:red;");
            status = false;
        }
        if(taAnamnesis.getText().equalsIgnoreCase("")){
            taAnamnesis.setPromptText("Anamnesis Tidak Boleh Kosong ! ");
            taAnamnesis.setStyle("-fx-prompt-text-fill:red");
            status = false;
        }
        if(taDiagnosis.getText().equalsIgnoreCase("")){
            taDiagnosis.setPromptText("Diagnosis Tidak Boleh Kosong ! ");
            taDiagnosis.setStyle("-fx-prompt-text-fill:red");
            status = false;
        }
        if(selectedActions.isEmpty()){
            tfFindAction.setPromptText("Cari Tindakan di Sini . Tindakan Tidak Boleh Kosong !");
            tfFindAction.setStyle("-fx-prompt-text-fill:red");
            status = false;
        }

        return status;
    }

    //clear form
    private void clearForm(){
       tfVeterinarianId.clear();
       tfVeterinarianId.setPromptText("ID Dokter");
       tfVeterinarianId.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
       tfPetWeight.clear();
       tfPetWeight.setPromptText("Berat Badan");
       tfPetWeight.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
       taAnamnesis.clear();
       taAnamnesis.setPromptText("Anamnesis");
       taAnamnesis.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
       taDiagnosis.clear();
       taDiagnosis.setPromptText("Diagnosis");
       taDiagnosis.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
       listPrescription.setItems(null);
       listActionData.setItems(null);
       tfFindAction.setPromptText("Cari Tindakan");
       tfFindAction.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
    }

    private void clearPetData(){
        lblPetId.setText("ID");
        lblPetId.setStyle("-fx-text-fill:black");
        lblPetName.setText("Nama");
        lblPetKind.setText("Jenis / Ras");
        lblPetGender.setText("Jenis Kelamin");
        lblPetDob.setText("Tanggal Lahir");
        lblPetWeight.setText("Berat Badan");
        lblOwnerId.setText("ID Pemilik");
        lblOwnerName.setText("Nama Pemilik");
        imgPet.setImage(null);
    }

    private void clearVeterinarianData(){
        lblVeterinarianId.setText("ID Dokter");
        lblVeterinarianId.setStyle("-fx-prompt-text-fill:rgba(0,0,0,0.5)");
        lblVeterinarianName.setText("Nama");
        lblVeterinarianId.setStyle("fx-text-fill:black");
    }
}
