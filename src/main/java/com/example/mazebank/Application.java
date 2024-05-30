package com.example.mazebank;
import com.example.mazebank.Core.Models.Model;
import javafx.stage.Stage;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage){
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}

