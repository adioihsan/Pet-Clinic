package com.pet.clinic.controller.payment;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.pet.clinic.helper.ConfirmationDialog;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.*;
import com.pet.clinic.model.dao.PetDao;
import com.pet.clinic.model.dao.PetOwnerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InvoicePrintViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox boxBill;

    @FXML
    private TableView<ActionsData> tblServices;

    @FXML
    private TableColumn<ActionsData, String> colServiceName;

    @FXML
    private TableColumn<ActionsData, Double> colServiceAmount;

    @FXML
    private TableView<Prescription> tblPrescription;

    @FXML
    private TableColumn<Prescription, String> colMedicineName;

    @FXML
    private TableColumn<Prescription, Integer> colMedicineQuantity;

    @FXML
    private TableColumn<Prescription, Double> colMedicinePriceTotal;

    @FXML
    private TableView<InvoicesData> tblOtherItem;

    @FXML
    private TableColumn<InvoicesData, String> colOtherItem;

    @FXML
    private TableColumn<InvoicesData, Integer> colOtherQuantity;

    @FXML
    private TableColumn<InvoicesData, Double> colOtherPriceTotal;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblInvoiceId;

    @FXML
    private Label lblPetOwner;

    @FXML
    private Label lblPet;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblPayDate;

    @FXML
    private JFXButton btnPrint;

    @FXML
    void initialize() {
        //table services
        colServiceName.setCellValueFactory(new PropertyValueFactory<ActionsData,String>("actionName"));
        colServiceAmount.setCellValueFactory(new PropertyValueFactory<ActionsData,Double>("price"));
        //end of table service;

        //table prescription

        colMedicineName.setCellValueFactory(new PropertyValueFactory<Prescription,String>("medicineName"));
        colMedicineQuantity.setCellValueFactory(new PropertyValueFactory<Prescription,Integer>("amount"));
        colMedicinePriceTotal.setCellValueFactory(new PropertyValueFactory<Prescription,Double>("priceTotal"));
        //end of prescription

        //table other
        colOtherItem.setCellValueFactory(new PropertyValueFactory<InvoicesData,String>("item"));
        colOtherQuantity.setCellValueFactory(new PropertyValueFactory<InvoicesData,Integer>("quantity"));
        colOtherPriceTotal.setCellValueFactory(new PropertyValueFactory<InvoicesData,Double>("priceTotal"));

        //print
        btnPrint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PrinterJob job = PrinterJob.createPrinterJob();
                if(job != null){
                    job.showPrintDialog(boxBill.getScene().getWindow());
                    job.printPage(boxBill);
                    job.endJob();
                }
                else
                    Message.showFailed("Printer Tidak di Temukan");
            }
        });
    }

    public TableView<ActionsData> getTblServices() {
        return tblServices;
    }
    public TableView<InvoicesData> getTblOtherItem() {
        return tblOtherItem;
    }

    public TableView<Prescription> getTblPrescription() {
        return tblPrescription;
    }

    public Label getLblTotalAmount() {
        return lblTotalAmount;
    }

    public Label getLblInvoiceId() {
        return lblInvoiceId;
    }

    public Label getLblPetOwner() {
        return lblPetOwner;
    }

    public Label getLblPet() {
        return lblPet;
    }

    public Label getLblUser() {
        return lblUser;
    }

    public Label getLblPayDate() {
        return lblPayDate;
    }

    public JFXButton getBtnPrint() {
        return btnPrint;
    }

    public VBox getBoxBill() {
        return boxBill;
    }

}
