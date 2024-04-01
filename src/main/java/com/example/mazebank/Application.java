package com.example.mazebank;
import com.example.mazebank.Models.Model;
import com.example.mazebank.Views.ViewFactory;
import javafx.stage.Stage;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage){
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}

