package com.pet.clinic.controller.payment;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.pet.clinic.App;
import com.pet.clinic.controller.login.ValidateUser;
import com.pet.clinic.database.DbConnect;
import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import com.pet.clinic.helper.Popup;
import com.pet.clinic.helper.Report;
import com.pet.clinic.model.*;
import com.pet.clinic.model.dao.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CreateInvoiceController {

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

    @FXML
    private Text txtAddress;

    @FXML
    private TableView<ActionsData> tblServices;

    @FXML
    private TableColumn<ActionsData, String> colServiceName;

    @FXML
    private TableColumn<ActionsData, String> colServiceDesc;

    @FXML
    private TableColumn<ActionsData, Double> colServiceAmount;

    @FXML
    private TableView<Prescription> tblPrescription;

    @FXML
    private TableColumn<Prescription, String> colMedicineName;

    @FXML
    private TableColumn<Prescription, String> colMedicineDesc;

    @FXML
    private TableColumn<Prescription, Integer> colMedicineQuantity;

    @FXML
    private TableColumn<Prescription, Double> colMedicinePrice;

    @FXML
    private TableColumn<Prescription, Double> colMedicinePriceTotal;

    @FXML
    private TableView<MedicRecord> tblServiceInfo;

    @FXML
    private TableColumn<MedicRecord, Integer> colServiceInfoId;

    @FXML
    private TableColumn<MedicRecord, LocalDate> colServiceInfoDate;

    @FXML
    private TableColumn<MedicRecord, String> colServiceInfoVet;

    @FXML
    private TableColumn<MedicRecord, String> colServiceInfoStatus;

    @FXML
    private TableView<InvoicesData> tblOtherItem;

    @FXML
    private TableColumn<InvoicesData, String> colOtherItem;

    @FXML
    private TableColumn<InvoicesData, String> colOtherDesc;

    @FXML
    private TableColumn<InvoicesData, Integer> colOtherQuantity;

    @FXML
    private TableColumn<InvoicesData, Double> colOtherPrice;

    @FXML
    private TableColumn<InvoicesData, Double> colOtherPriceTotal;

    @FXML
    private TextField tfItem;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfAmount;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private JFXButton btnSave;

    @FXML
    private GridPane gridPayment;

    private final ObservableList<MedicRecord> serviceInfoList = FXCollections.observableArrayList();
    private final ObservableList<ActionsData> serviceList = FXCollections.observableArrayList();
    private final ObservableList<Prescription> medicineList = FXCollections.observableArrayList();
    private final ObservableList<InvoicesData> otherList = FXCollections.observableArrayList();
    private final ObservableList<InvoicesData> allInvoicesList = FXCollections.observableArrayList();
    private Double totalPrice = 0d;
    private  int medicRecordId = 0;
    private int petId = 0;

    @FXML
    void initialize() {
        //button Rules
        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        tfPetId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPetId.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        tfQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfQuantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tfAmount.setTextFormatter(formatter);



        //find pet
        tfPetId.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                findPet();
            }
        });

        // table serviceInfo
        tblServiceInfo.setItems(serviceInfoList);
        colServiceInfoId.setCellValueFactory(new PropertyValueFactory<MedicRecord,Integer>("medicRecordId"));
        colServiceInfoDate.setCellValueFactory(new PropertyValueFactory<MedicRecord,LocalDate>("recordDate"));
        colServiceInfoVet.setCellValueFactory(new PropertyValueFactory<MedicRecord,String>("veterinarianName"));
        colServiceInfoStatus.setCellValueFactory(new PropertyValueFactory<MedicRecord,String>("status"));

        tblServiceInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tblServiceInfo.getSelectionModel().getSelectedItem() != null){
                    MedicRecord medicRecord = tblServiceInfo.getSelectionModel().getSelectedItem();
                    medicRecordId = medicRecord.getMedicRecordId();
                    if(medicRecord.getStatus().equalsIgnoreCase("Sudah Bayar")) {
                        if(ConfirmationDialog.showMakeSure("Pembayaran Sudah Pernah di Lakukan , " +
                                "Lakukan Pemabayaran Ulang ?")) {
                            loadTblService(medicRecordId);
                            loadTblPrescription(medicRecordId);
                            clearTblOther();
                            countAllPrice();
                        }
                        else{
                            clearTblOther();
                            clearTblService();
                            clearTblPrescription();
                            countAllPrice();
                        }
                    }
                    else{
                        loadTblService(medicRecord.getMedicRecordId());
                        loadTblPrescription(medicRecord.getMedicRecordId());
                        clearTblOther();
                        countAllPrice();
                    }

                }
            }
        });
        //end of table service info

        //table services
        tblServices.setItems(serviceList);
        colServiceName.setCellValueFactory(new PropertyValueFactory<ActionsData,String>("actionName"));
        colServiceDesc.setCellValueFactory(new PropertyValueFactory<ActionsData,String>("description"));
        colServiceAmount.setCellValueFactory(new PropertyValueFactory<ActionsData,Double>("price"));
        //end of table service;

        //table prescription
        tblPrescription.setItems(medicineList);
        colMedicineName.setCellValueFactory(new PropertyValueFactory<Prescription,String>("medicineName"));
        colMedicineDesc.setCellValueFactory(new PropertyValueFactory<Prescription,String>("description"));
        colMedicineQuantity.setCellValueFactory(new PropertyValueFactory<Prescription,Integer>("amount"));
        colMedicinePrice.setCellValueFactory(new PropertyValueFactory<Prescription,Double>("price"));
        colMedicinePriceTotal.setCellValueFactory(new PropertyValueFactory<Prescription,Double>("priceTotal"));
        //end of prescription

        //table other
        colOtherItem.setCellValueFactory(new PropertyValueFactory<InvoicesData,String>("item"));
        colOtherDesc.setCellValueFactory(new PropertyValueFactory<InvoicesData,String>("description"));
        colOtherQuantity.setCellValueFactory(new PropertyValueFactory<InvoicesData,Integer>("quantity"));
        colOtherPrice.setCellValueFactory(new PropertyValueFactory<InvoicesData,Double>("price"));
        colOtherPriceTotal.setCellValueFactory(new PropertyValueFactory<InvoicesData,Double>("priceTotal"));

        //add other payment
        tblOtherItem.setItems(otherList);
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addOtherPayment();
                countAllPrice();
            }
        });
        tblOtherItem.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tblOtherItem.getSelectionModel().getSelectedItem() != null){
                    InvoicesData invoicesData = tblOtherItem.getSelectionModel().getSelectedItem();
                    if(ConfirmationDialog.showDelete("Hapus Biaya "+invoicesData.getItem()+" ?")){
                        otherList.remove(invoicesData);
                        countAllPrice();
                    }

                }
            }
        });
        //end of add other payment
        //add total price/ amount

        //save haha
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    if (!isFieldNull()) {
                        Invoice invoice = saveInvoicesData(medicRecordId);
                        if (invoice != null) {
                            if (ConfirmationDialog.showMakeSure("Pembayaran Berhasi di Simapan, Cetak Struk ?")) {
                                printInVoice(invoice);
                            }
                        } else Message.showFailed();
                    } else Message.showFailed("Tidak ada yang harus di bayar");
                }
        });



// END OF INITIALIZE
    }
    private void findPet(){
        if (!tfPetId.getText().equalsIgnoreCase("")) {
            petId = Integer.valueOf(tfPetId.getText());
            int ownerId = setPet(petId);
            if (ownerId != 0) {
                boolean isOwner = setOwner(ownerId);
                if (isOwner) {
                    lblSearchStatus.setVisible(false);
                    loadTblServiceInfo(petId);
                }
            } else {
                lblSearchStatus.setText("Peliharaan Tidak di Temukan");
                lblSearchStatus.setVisible(true);
                clearTblOther();
                clearTblService();
                clearTblPrescription();
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
            txtAddress.setText(petOwner.getAddress());
            return true;
        } else {
            return false;
        }
    }
    private void loadTblServiceInfo(int petId){
        ArrayList<MedicRecord> listFromDb= MedicRecordDao.getMedicRecord(petId);
        serviceInfoList.setAll(listFromDb);
    }
    private void loadTblService(int medicRecordId){
        ArrayList<ActionsData> listFromDb = ActionsDataDao.getActionsData(medicRecordId);
        serviceList.setAll(listFromDb);
    }
    private void loadTblPrescription(int medicRecordId){
        ArrayList<Prescription> listFormDb = PrescriptionDao.getPrescription(medicRecordId);
        medicineList.setAll(listFormDb);
    }
    private void addOtherPayment(){
        InvoicesData invoicesData = new InvoicesData();
        invoicesData.setItem(tfItem.getText());
        invoicesData.setDescription(tfDescription.getText());
        invoicesData.setItemType("Lainnya");
        invoicesData.setQuantity(Integer.parseInt(tfQuantity.getText()));
        invoicesData.setPrice(Double.parseDouble(tfAmount.getText()));
        otherList.add(invoicesData);
    }
    private void countAllPrice(){
        totalPrice = 0d;
        Iterator<ActionsData> itrService = serviceList.listIterator();
        Iterator<Prescription> itrPrescription = medicineList.listIterator();
        Iterator<InvoicesData> itrInvoicesData = otherList.listIterator();
        while(itrService.hasNext()){
            totalPrice += itrService.next().getPrice();
        }
        while(itrPrescription.hasNext()){
            totalPrice += itrPrescription.next().getPriceTotal();
        }
        while(itrInvoicesData.hasNext()){
            totalPrice += itrInvoicesData.next().getPriceTotal();
        }
        lblTotalAmount.setText(totalPrice.toString());
    }
    private void moveToInvoices(int medicRecordId,int invoiceId){
        Iterator<ActionsData> itrService = serviceList.listIterator();
        Iterator<Prescription> itrPrescription = medicineList.listIterator();
        Iterator<InvoicesData> itrInvoicesData = otherList.listIterator();
        while(itrService.hasNext()){
            InvoicesData invoicesData = new InvoicesData();
            ActionsData actionsData = itrService.next();

            invoicesData.setInvoiceId(invoiceId);
            invoicesData.setItem(actionsData.getActionName());
            invoicesData.setItemType("Layanan");
            invoicesData.setDescription(actionsData.getDescription());
            invoicesData.setQuantity(1);
            invoicesData.setPrice(actionsData.getPrice());
            invoicesData.setPriceTotal(actionsData.getPrice());
            allInvoicesList.add(invoicesData);

        }
        while(itrPrescription.hasNext()){
            InvoicesData invoicesData = new InvoicesData();
            Prescription prescription = itrPrescription.next();

            invoicesData.setInvoiceId(invoiceId);
            invoicesData.setItem(prescription.getMedicineName());
            invoicesData.setItemType("Obat");
            invoicesData.setDescription(prescription.getDescription());
            invoicesData.setQuantity(prescription.getAmount());
            invoicesData.setPrice(prescription.getPrice());
            invoicesData.setPriceTotal(prescription.getPriceTotal());
            allInvoicesList.add(invoicesData);
        }
        while(itrInvoicesData.hasNext()){
            InvoicesData invoicesData = itrInvoicesData.next();
            invoicesData.setInvoiceId(invoiceId);
            allInvoicesList.add(invoicesData);
        }
    }
    private Invoice saveInvoicesData(int medicRecordId){
        //create an invoice
        Invoice invoice = new Invoice();
        invoice.setMedicRecordId(medicRecordId);
        invoice.setPetId(petId);
        invoice.setCreateDate(LocalDate.now());
        invoice.setTotalAmount(totalPrice);
        invoice.setUserId(ValidateUser.getLoggedUser().getId());
        int invoiceId = InvoiceDao.insertInvoice(invoice);
        invoice.setInvoiceId(invoiceId);
        if( invoiceId != 0) {
            //insert invoice data
            moveToInvoices(medicRecordId,invoiceId);
            Iterator<InvoicesData> itr = allInvoicesList.listIterator();
            while (itr.hasNext()) {
                InvoicesDataDao.insertInvoicesData(itr.next());
            };
            return  invoice;
        }
        else{
            return null;
        }
    }
    //clear things up
    private void clearTblOther(){
        otherList.clear();
        tfAmount.clear();
        tfDescription.clear();
        tfItem.clear();
        tfQuantity.clear();
    }
    private void clearTblService(){
        serviceList.clear();
    }private void clearTblPrescription(){
        medicineList.clear();
    }

    private void  printInVoice(Invoice invoice){
        Pet pet = PetDao.getPet(invoice.getPetId());
        PetOwner petOwner = PetOwnerDao.getOwner(pet.getOwnerId());
        User user = ValidateUser.getLoggedUser();
        Popup popup = new Popup();
        InvoicePrintViewController ipc = (InvoicePrintViewController) popup.load(btnSave.getScene().getWindow(),"payment/invoicePrintView");
        ipc.getTblServices().setItems(serviceList);
        ipc.getTblPrescription().setItems(medicineList);
        ipc.getTblOtherItem().setItems(otherList);
        ipc.getLblTotalAmount().setText(String.valueOf(totalPrice));
        ipc.getLblPet().setText("("+pet.getId()+") "+pet.getName());
        ipc.getLblPetOwner().setText("("+petOwner.getId()+") "+petOwner.getFirstName()+" "+petOwner.getLastName());
        ipc.getLblInvoiceId().setText(String.valueOf(invoice.getInvoiceId()));
        ipc.getLblUser().setText("("+user.getId()+") "+user.getFirstName()+" "+user.getLastName());
        ipc.getLblPayDate().setText(invoice.getCreateDate().toString());
    }

    private boolean isFieldNull(){
        if(serviceInfoList.isEmpty()) return true;
        if(!medicineList.isEmpty() || !serviceList.isEmpty() || !otherList.isEmpty()){
            return false;
        }
        else
        return true;
    }

}

