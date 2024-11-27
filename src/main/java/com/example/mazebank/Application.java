package com.example.mazebank;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Views.ViewFactory;
import com.google.zxing.WriterException;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException, WriterException {
        System.out.println("Java version - " + System.getProperty("java.version"));
        System.out.println("JavaFX version - " + System.getProperty("javafx.version"));

        System.out.println("[LOG][Security] - Security key for authenticator: " + Security.generateSecretKey());
        Model.getInstance().getViewFactory().showLoginWindow();
    }



}

