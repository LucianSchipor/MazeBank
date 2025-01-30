package com.example.mazebank;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import com.example.mazebank.Repositories.BankInfo.DB_BankInfo;
import javafx.stage.Stage;
import javax.crypto.SecretKey;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Java version - " + System.getProperty("java.version"));
        System.out.println("JavaFX version - " + System.getProperty("javafx.version"));
        DB_BankInfo.getBankInfo();
        System.out.println(EncryptionManager.encrypt("1000.0", KeyManager.loadKey()));
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}

