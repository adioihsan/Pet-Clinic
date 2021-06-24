package com.pet.clinic.helper;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSpinner;
import com.pet.clinic.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Loading{
    private static Stage stage = new Stage();
    private static Scene scene;
    public  static void load(Window window) throws IOException {
        scene = new Scene(loadFXML("helper/loading"));
        stage.setScene(scene);
        stage.setTitle("Pet Clinic");
        stage.setMaximized(false);
        stage.initOwner(window);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
