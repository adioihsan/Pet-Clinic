package com.pet.clinic.controller.medicine;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.pet.clinic.helper.Message;
import com.pet.clinic.model.Medicine;
import com.pet.clinic.model.dao.MedicineDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tornadofx.control.DateTimePicker;

public class MedicineDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private TextField tfFind;

    @FXML
    private JFXTreeTableView<Medicines> tvMedicine;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfFill;

    @FXML
    private TextField tfLimit;

    @FXML
    private JFXButton btnAddLimit;

    @FXML
    private TextField tfStock;

    @FXML
    private TextField tfUnit;

    @FXML
    private DateTimePicker dpExpired;

    @FXML
    private TextField tfBuyPrice;

    @FXML
    private TextField tfSellPrice;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private Label lblMedicineId;

    @FXML
    private CheckBox chkIsNewMedicine;

    @FXML
    private CheckBox chkIsAddStock;

    private int medicineOldStock=0;

    @FXML
    void initialize() {

        // set limit rule
        tfLimit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfLimit.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Craeate table column
        JFXTreeTableColumn<Medicines, String> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setPrefWidth(70);
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(idCol.validateValue(param)) return param.getValue().getValue().id;
            else return idCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> nameCol= new JFXTreeTableColumn<>("Nama Obat");
        nameCol.setPrefWidth(100);
        nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(nameCol.validateValue(param)) return param.getValue().getValue().name;
            else return nameCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> fillCol = new JFXTreeTableColumn<>("Isi");
        fillCol.setPrefWidth(80);
        fillCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(fillCol.validateValue(param)) return param.getValue().getValue().fill;
            else return fillCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> unitCol = new JFXTreeTableColumn<>("Satuan");
        unitCol.setPrefWidth(80);
        unitCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(unitCol.validateValue(param)) return param.getValue().getValue().unit;
            else return unitCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> stcokCol = new JFXTreeTableColumn<>("Stok");
        stcokCol.setPrefWidth(70);
        stcokCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(stcokCol.validateValue(param)) return param.getValue().getValue().stock;
            else return stcokCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> expiredCol = new JFXTreeTableColumn<>("Kadaluarsa");
        expiredCol.setPrefWidth(100);
        expiredCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(expiredCol.validateValue(param)) return param.getValue().getValue().expired;
            else return expiredCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> buyPriceCol = new JFXTreeTableColumn<>("Harga Beli");
        buyPriceCol.setPrefWidth(110);
        buyPriceCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(buyPriceCol.validateValue(param)) return param.getValue().getValue().buyPrice;
            else return buyPriceCol.getComputedValue(param);
        });
        JFXTreeTableColumn<Medicines, String> sellPriceCol = new JFXTreeTableColumn<>("Harga Jual");
        sellPriceCol.setPrefWidth(110);
        sellPriceCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Medicines, String> param) ->{
            if(sellPriceCol.validateValue(param)) return param.getValue().getValue().sellPrice;
            else return sellPriceCol.getComputedValue(param);
        });

        tvMedicine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(chkIsAddStock.isSelected() && tvMedicine.getSelectionModel().getSelectedItem() != null){
                    Medicines medicines = tvMedicine.getSelectionModel().getSelectedItem().getValue();
                    setMedicineField(medicines);
                    setEnableForm(true);
                }
            }
        });

        // add Columns to table
        tvMedicine.getColumns().setAll(idCol,nameCol,fillCol,unitCol,stcokCol,expiredCol,buyPriceCol,sellPriceCol);

        //set Tables rules
        tvMedicine.setEditable(false);
        tvMedicine.setShowRoot(false);

        //load data into table;
        loadTableData(100);

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

        //check button
        chkIsAddStock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(chkIsAddStock.isSelected()){
                    chkIsNewMedicine.setSelected(false);
                    setEnableForm(false);
                }
                else{
                    chkIsNewMedicine.setSelected(true);
                    clearForm();
                }
            }
        });
        chkIsNewMedicine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(chkIsNewMedicine.isSelected()){
                    chkIsAddStock.setSelected(false);
                    setEnableForm(true);
                    clearForm();
                }
                else{
                    chkIsAddStock.setSelected(true);
                }
            }
        });
        //add or save button
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(chkIsNewMedicine.isSelected()) {
                    int id = 0;
                    if (isNotNull()) id = saveMedicine();
                    if (id != 0) {
                        Message.showSuccess("Obat Berhasil di Tambahkan");
                        loadTableData(Integer.parseInt(tfLimit.getText()));
                    } else {
                        Message.showFailed("Terjadi Kesalahan" +
                                "Obat Gagal di Tambahkan");
                    }
                }
                else if(chkIsAddStock.isSelected()){
                    if(updateMedicine()){
                        Message.showSuccess("Data Obat Berhasil di Update");
                        loadTableData(Integer.parseInt(tfLimit.getText()));
                        }
                    else {
                        Message.showFailed("Terjadi Kesalahan" +
                                "Data Obat Gagal di Update");
                        }
                    }
                }
        });
    }

    //load data into table
    private void loadTableData(int limit){
        ArrayList<Medicine> medicinesFromDb = MedicineDao.getAllMedicine(limit);
        ObservableList<Medicines> medicinesList = FXCollections.observableArrayList();
        Iterator<Medicine> itr = medicinesFromDb.iterator();
        while (itr.hasNext()){
            Medicine medicine = itr.next();
            medicinesList.add(new Medicines(String.valueOf(medicine.getId()),medicine.getName(),medicine.getFill(),
                    String.valueOf(medicine.getStock()),medicine.getUnit(),medicine.getExpired().toString(),
                    String.valueOf(medicine.getSellPrice()),
                    String.valueOf(medicine.getBuyPrice())));
        }
        TreeItem<Medicines> rootAll = new RecursiveTreeItem<Medicines>(medicinesList, RecursiveTreeObject::getChildren);
        tvMedicine.setRoot(rootAll);
    }

    private void loadTableData(String keyword , int limit){
        ArrayList<Medicine> medicinesFromDb = MedicineDao.findMedicine(keyword , limit);
        ObservableList<Medicines> medicinesList = FXCollections.observableArrayList();
        Iterator<Medicine> itr = medicinesFromDb.iterator();
        while (itr.hasNext()){
            Medicine medicine = itr.next();
            medicinesList.add(new Medicines(String.valueOf(medicine.getId()),medicine.getName(),medicine.getFill(),
                    String.valueOf(medicine.getStock()),medicine.getUnit(),medicine.getExpired().toString(),
                    String.valueOf(medicine.getSellPrice()),
                    String.valueOf(medicine.getBuyPrice())));
        }
        TreeItem<Medicines> rootFind = new RecursiveTreeItem<Medicines>(medicinesList, RecursiveTreeObject::getChildren);
        tvMedicine.setRoot(rootFind);
    }
    private void setMedicineField(Medicines medicine){
        lblMedicineId.setText(medicine.id.getValue());
        tfName.setText(medicine.name.getValue());
        tfBuyPrice.setText(medicine.buyPrice.getValue());
        tfStock.setPromptText(medicine.stock.getValue() +" + ");
        tfUnit.setText(medicine.unit.getValue());
        tfSellPrice.setText(medicine.sellPrice.getValue());
        tfFill.setText(medicine.fill.getValue());
        dpExpired.setValue(LocalDate.parse(medicine.expired.getValue()));
        medicineOldStock = Integer.parseInt(medicine.stock.getValue());

    }

    private int saveMedicine(){
        Medicine medicine = new Medicine();
        medicine.setName(tfName.getText());
        medicine.setFill(tfFill.getText());
        medicine.setUnit(tfUnit.getText());
        medicine.setStock(Integer.parseInt(tfStock.getText()));
        medicine.setExpired(dpExpired.getValue());
        medicine.setSellPrice(Double.parseDouble(tfSellPrice.getText()));
        medicine.setBuyPrice(Double.parseDouble(tfSellPrice.getText()));
        return MedicineDao.insertMedicine(medicine);
    }
    private boolean updateMedicine(){
        Medicine medicine = new Medicine();
        medicine.setId(Integer.parseInt(lblMedicineId.getText()));
        medicine.setName(tfName.getText());
        medicine.setFill(tfFill.getText());
        medicine.setUnit(tfUnit.getText());
        medicine.setStock(Integer.parseInt(tfStock.getText())+medicineOldStock);
        medicine.setIn(Integer.parseInt(tfStock.getText()));
        medicine.setExpired(dpExpired.getValue());
        medicine.setSellPrice(Double.parseDouble(tfSellPrice.getText()));
        medicine.setBuyPrice(Double.parseDouble(tfSellPrice.getText()));
        return  MedicineDao.updateMedicine(medicine);
    }
    private boolean isNotNull(){
        boolean status = true;
        if(tfName.getText().equalsIgnoreCase("")) status=false;
        if(tfUnit.getText().equalsIgnoreCase("")) status=false;
        if(tfFill.getText().equalsIgnoreCase("")) status=false;
        if(tfStock.getText().equalsIgnoreCase("")) status=false;
        if(tfBuyPrice.getText().equalsIgnoreCase("")) status=false;
        if(tfSellPrice.getText().equalsIgnoreCase("")) status=false;
        if(dpExpired.getValue() == null) status=false;
        return status;
    }
    private void clearForm(){
        tfFill.clear();
        tfUnit.clear();
        tfSellPrice.clear();
        tfBuyPrice.clear();
        tfName.clear();
        tfStock.clear();
        dpExpired.setValue(null);
        lblMedicineId.setText("ID");
    }
    private void setEnableForm(Boolean isTrue){
        tfFill.setEditable(isTrue);
        tfUnit.setEditable(isTrue);
        tfSellPrice.setEditable(isTrue);
        tfBuyPrice.setEditable(isTrue);
        tfName.setEditable(isTrue);
        tfStock.setEditable(isTrue);
        dpExpired.setEditable(isTrue);
    }


}
class Medicines extends RecursiveTreeObject<com.pet.clinic.controller.medicine.Medicines> {
    StringProperty id;
    StringProperty name;
    StringProperty fill;
    StringProperty stock;
    StringProperty unit;
    StringProperty expired;
    StringProperty buyPrice;
    StringProperty sellPrice;

    public Medicines(String id, String name, String fill, String stock,
                     String unit, String expired, String buyPrice, String sellPrice) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.fill = new SimpleStringProperty(fill);
        this.stock = new SimpleStringProperty(stock);
        this.unit = new SimpleStringProperty(unit);
        this.expired = new SimpleStringProperty(expired);
        this.buyPrice = new SimpleStringProperty(buyPrice);
        this.sellPrice = new SimpleStringProperty(sellPrice);
    }

}