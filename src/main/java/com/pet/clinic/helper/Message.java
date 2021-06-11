package com.pet.clinic.helper;

import javafx.scene.control.Alert;

public class Message {

    public static void showSuccess(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Data Berhasil di Simpan.");
        success.setTitle("Pet Clinic");
        success.setHeaderText("Berhasil");
        success.show();
    }
    public static void showFailed(){
        Alert failed = new Alert(Alert.AlertType.ERROR, "Terjadi Kesalahan !. Data Gagal di Simpan.");
        failed.setTitle("Pet Clinic");
        failed.setHeaderText("Gagal");
        failed.show();
    }

    public static void showSuccess(String message){
        Alert success = new Alert(Alert.AlertType.INFORMATION, message);
        success.setTitle("Pet Clinic");
        success.setHeaderText("Berhasil");
        success.show();
    }

    public static void showFailed(String message){
        Alert failed = new Alert(Alert.AlertType.ERROR, message);
        failed.setTitle("Pet Clinic");
        failed.setHeaderText("Gagal");
        failed.show();
    }

}
