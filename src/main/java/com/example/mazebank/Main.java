package com.example.mazebank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Calea absolută către fișierul FXML
        File file = new File("C:/Users/Lucian/IdeaProjects/MazeBank/src/main/resources/Fxml/Login.fxml"); // Înlocuiește cu calea absolută către fișierul FXML

        // Convertim calea fișierului în URI
        URI uri = file.toURI();

        // Încarcăm fișierul FXML
        Parent root = FXMLLoader.load(uri.toURL());
        primaryStage.setTitle("UnitBv Bank");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    }
