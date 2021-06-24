package com.pet.clinic.controller.admin;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.pet.clinic.helper.Message;
import com.pet.clinic.model.Action;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetKind;
import com.pet.clinic.model.dao.ActionDao;
import com.pet.clinic.model.dao.PetDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class DynamicDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxHeader;

    @FXML
    private TableView<PetKind> tblPetKind;

    @FXML
    private TableColumn<PetKind, Integer> colKindId;

    @FXML
    private TableColumn<PetKind, String> colKindName;

/*    @FXML
    private TextField tfKindName;*/

    @FXML
    private TextField tfNewKindName;

    @FXML
    private JFXButton btnSaveKind;

    @FXML
    private TableView<Action> tblAction;

    @FXML
    private TableColumn<Action, Integer> colActionId;

    @FXML
    private TableColumn<Action, String> colActionName;

    @FXML
    private TableColumn<Action, Double> colActionPrice;

    @FXML
    private TextField tfActionName;

    @FXML
    private TextField tfActionPrice;

    @FXML
    private JFXButton btnSaveAction;

    private ObservableList<PetKind> petKindList = FXCollections.observableArrayList();

    private ObservableList<Action> actionList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
     // button rules
        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        tfActionPrice.setTextFormatter(formatter);
        // pet kind table
        tblPetKind.setItems(petKindList);
        colKindId.setCellValueFactory(new PropertyValueFactory<PetKind,Integer>("petKindId"));
        colKindName.setCellValueFactory(new PropertyValueFactory<PetKind,String>("name"));
        loadPetKinds();
        //end of kind table

        //action table
        tblAction.setItems(actionList);
        colActionId.setCellValueFactory(new PropertyValueFactory<Action,Integer>("actionId"));
        colActionName.setCellValueFactory(new PropertyValueFactory<Action,String>("name"));
        colActionPrice.setCellValueFactory(new PropertyValueFactory<Action,Double>("price"));
        loadAction();
        //end action table


        //save kind
        btnSaveKind.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!tfNewKindName.getText().equalsIgnoreCase("")){
                    if(savePetKind()){
                        Message.showSuccess("Jenis Peliharaan Baru Berhasil di Simpan");
                        loadPetKinds();
                    }
                    else
                        Message.showFailed("Terjadi Kesalahan ,Jenis Peliharaan Baru Gagal di Simpan !");
                }
            }
        });

        //save action
        btnSaveAction.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!tfActionName.getText().equalsIgnoreCase("")&&
                        !tfActionPrice.getText().equalsIgnoreCase("")){
                    if(saveAction()){
                        Message.showSuccess("Tindakan Baru Berhasil di Simpan");
                        loadAction();
                    }
                    else{
                        Message.showFailed("Terjadi Kesalahan,Tindakan Baru Gagal di Simpan !");
                    }
                }
            }
        });

    }

    private void loadPetKinds(){
        ArrayList<PetKind> petKinds = PetDao.getPetKind();
        petKindList.setAll(petKinds);
    }
    private boolean savePetKind(){
        return PetDao.insertPetKind(tfNewKindName.getText());
    }
    private void loadAction(){
        ArrayList<Action> actions = ActionDao.getAllAction();
        actionList.setAll(actions);
    }
    private boolean saveAction(){
        return ActionDao.insertAction(tfActionName.getText(),Double.parseDouble(tfActionPrice.getText()));
    }

}
