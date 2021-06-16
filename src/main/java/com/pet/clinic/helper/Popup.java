package com.pet.clinic.helper;

import com.jfoenix.controls.JFXDecorator;
import com.pet.clinic.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Popup {

    private  Stage stage = new Stage();
    private  JFXDecorator decorator;
    private  Scene scene;

    public  Object load(Window window, String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ fxml + ".fxml"));
        try {
            decorator = new JFXDecorator(stage,fxmlLoader.load(),false,false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(decorator);
/*        String uri = App.class.getClass().getResource("css/windowDecorator.css").toExternalForm();
        scene.getStylesheets().add(uri);*/
        stage.setScene(scene);
        stage.setTitle("Pet Clinic");
        stage.setMaximized(false);
        stage.initOwner(window);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        return fxmlLoader.getController();
    }

}


