package com.example.mazebank.Repositories.DBUtils;

import com.example.mazebank.Core.Models.CheckingAccount;
import com.example.mazebank.Repositories.DB_Transactions;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DB_BankAccounts {
    @SuppressWarnings("SqlNoDataSourceInspection")
    public static LinkedHashMap<Integer,CheckingAccount> GetBankAccounts(int user_id) {
        Connection connection = null;
        PreparedStatement psCheckUserExists;
        LinkedHashMap<Integer,CheckingAccount> accounts = new LinkedHashMap<>();
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");
            psCheckUserExists.setInt(1, user_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("account_balance");
                String currency = resultSet.getString("account_currency");
                String account_number = resultSet.getString("account_number");
                var newBankAccount =  new CheckingAccount(account_number, balance, currency);
                newBankAccount.setAccount_id(resultSet.getInt("account_id"));
                newBankAccount.setTransactions(DB_Transactions.GetBankAccountTransactions(account_id));
                accounts.put(account_id, newBankAccount);
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        }
        return accounts;
    }

    // Used for set the local Checking Account with updated from database Checking Account.
    // Returns same Checkin Account but updated
    public static CheckingAccount UpdateBankAccount_Local(CheckingAccount account) {
        Connection connection = null;
        PreparedStatement psCheckUserExists;
        PreparedStatement psGetBankAccountTransactions;
        List<CheckingAccount> accounts = new ArrayList<>();
        ResultSet resultSet;
        CheckingAccount newBankAccount = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE account_id = ?");
            psCheckUserExists.setInt(1, account.getAccount_id());
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                double balance = resultSet.getDouble("account_balance");
                newBankAccount =  new CheckingAccount(account.getAccountNumber(), balance, account.getCurrency());
                newBankAccount.setTransactions(DB_Transactions.GetBankAccountTransactions(account.getAccount_id()));
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        }
        return newBankAccount;
    }
}
