package com.example.mazebank;
import com.example.mazebank.Core.Models.Model;
import javafx.stage.Stage;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage){
        System.out.println("Java version - " + System.getProperty("java.version"));
        System.out.println("JavaFX version - " +  System.getProperty("javafx.version"));

        Model.getInstance().getViewFactory().showLoginWindow();


    }
}

