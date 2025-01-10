package com.example.mazebank;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.BankInfo.DB_BankInfo;
import com.google.zxing.WriterException;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException, WriterException {
        System.out.println("Java version - " + System.getProperty("java.version"));
        System.out.println("JavaFX version - " + System.getProperty("javafx.version"));
        DB_BankInfo.GetBankInfo();
        Model.getInstance().getViewFactory().showLoginWindow();
    }



}

