package com.pet.clinic.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationDialog {

    public static boolean showDelete(String text){
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION, text);
        delete.setTitle("Pet Clinic");
        delete.setHeaderText("Hapus");

        ButtonType btnYes = new ButtonType("Ya");
        ButtonType btnNo = new ButtonType("Tidak");
        delete.getButtonTypes().setAll(btnYes,btnNo);

        Optional<ButtonType> res = delete.showAndWait();
        if(res.get() == btnYes) return true;
        else return  false;
    }
    public static boolean showMakeSure(String text){
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION, text);
        delete.setTitle("Pet Clinic");
        delete.setHeaderText("Anda Yakin ?");

        ButtonType btnYes = new ButtonType("Ya");
        ButtonType btnNo = new ButtonType("Tidak");
        delete.getButtonTypes().setAll(btnYes,btnNo);

        Optional<ButtonType> res = delete.showAndWait();
        if(res.get() == btnYes) return true;
        else return  false;
    }
    public static boolean showDialog(String header,String message){
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION, message);
        delete.setTitle("Pet Clinic");
        delete.setHeaderText(header);

        ButtonType btnYes = new ButtonType("Ya");
        ButtonType btnNo = new ButtonType("Tidak");
        delete.getButtonTypes().setAll(btnYes,btnNo);

        Optional<ButtonType> res = delete.showAndWait();
        if(res.get() == btnYes) return true;
        else return  false;
    }
}
