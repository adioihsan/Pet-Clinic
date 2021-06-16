package com.pet.clinic;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static int userId;
    public static String userName;
    private static Scene scene;
    private static JFXDecorator decorator;
    @Override
    public void start(Stage stage) throws IOException {
        decorator = new JFXDecorator(stage,loadFXML("login/login"));
        String uri = getClass().getResource("css/windowDecorator.css").toExternalForm();
        scene = new Scene(decorator, 1280, 800);
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.setTitle("Pet Clinic");
        stage.setMaximized(false);
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        //scene.setRoot(loadFXML(fxml));
        decorator.setContent(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }
    

    public static void main(String[] args) {
        launch();
    }

}

/* Testing Push 3*/