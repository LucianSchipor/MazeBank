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
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
       Scene scene = new Scene(fxmlLoader.load());
       primaryStage.setScene(scene);
       primaryStage.show();
    }
}

