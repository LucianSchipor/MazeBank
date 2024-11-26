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
        Security();


    }


    private void Security() throws IOException, WriterException {
        String secretKey = "RAZ5OKOS63PBEB2U5PCLVWLBZKDW423N";
        String email = "schiporlucian20021@gmail.com";
        String companyName = "Maze Bank";
        String barCodeUrl = Security.getGoogleAuthenticatorBarCode(secretKey, email, companyName);
        startSecurityThread(barCodeUrl);
        Model.getInstance().getViewFactory().showQRCode(barCodeUrl);
        System.out.println(barCodeUrl);
    }


    private void startSecurityThread(String secretKey) throws IOException, WriterException {
        Thread securityThread = new Thread(() -> {
            String lastCode = null;
            while (true) {
                String code = Security.getTOTPCode(secretKey);
                if (!code.equals(lastCode)) {
                    System.out.println(code);
                }
                lastCode = code;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("[LOG] - " + e.getMessage());
                    break;
                }
                ;
            }
        });
        securityThread.setDaemon(true);
        securityThread.start();
    }
}

