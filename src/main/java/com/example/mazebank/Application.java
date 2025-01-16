package com.example.mazebank;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import com.example.mazebank.Repositories.BankInfo.DB_BankInfo;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Java version - " + System.getProperty("java.version"));
        System.out.println("JavaFX version - " + System.getProperty("javafx.version"));
        SecretKey key = null;
        System.out.println(EncryptionManager.encrypt("1000", KeyManager.loadKey()));
        DB_BankInfo.getBankInfo();
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}

