package com.example.mazebank.Repositories.BankAccounts;

import com.example.mazebank.Core.BankAccounts.CheckingAccount;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DB_BankAccounts {
    @SuppressWarnings("SqlNoDataSourceInspection")
    public static LinkedHashMap<String,CheckingAccount> GetBankAccounts(int user_id) throws SQLException {
        PreparedStatement psCheckUserExists;
        LinkedHashMap<String,CheckingAccount> accounts = new LinkedHashMap<>();
        ResultSet resultSet;
        var connection = DB_ConnectionManager.getInstance().GetConnection();
        try {
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");
            psCheckUserExists.setInt(1, user_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                String account_id = resultSet.getString("account_id");
                double balance = resultSet.getDouble("account_balance");
                String currency = resultSet.getString("account_currency");
                var newBankAccount =  new CheckingAccount(account_id, balance, currency);
                newBankAccount.setAccount_id(resultSet.getString("account_id"));
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
    public static void Local_UpdateBankAccountsAfterTransaction(CheckingAccount account) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        }
        catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        CheckingAccount newBankAccount = null;
        try {
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE account_id = ?");
            psCheckUserExists.setString(1, account.getAccount_id());
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                double balance = resultSet.getDouble("account_balance");
                account.setTransactions(DB_Transactions.GetBankAccountTransactions(account.getAccount_id()));
                account.setBalance(balance);
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
    }

    public static void DB_UpdateBankAccountsAfterTransaction(String sender, Double amount, String receiver){
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        PreparedStatement psInsertTransaction;
        PreparedStatement psGetBankAccount;
        ResultSet resultSet = null;
        Double sender_balance = (double) 0;
        Double receiver_balance = (double) 0;
        try {
            assert connection != null;
            psInsertTransaction = connection.prepareStatement(
                    "UPDATE bank_accounts " +
                            "SET account_balance = account_balance - ? " +
                            "WHERE account_id = ?");
            psInsertTransaction.setDouble(1, amount);
            psInsertTransaction.setString(2, sender);
            psInsertTransaction.executeUpdate();

            psInsertTransaction = connection.prepareStatement(
                    "UPDATE bank_accounts " +
                            "SET account_balance = account_balance + ? " +
                            "WHERE account_id = ?");
            psInsertTransaction.setDouble(1, amount);
            psInsertTransaction.setString(2, receiver);
            psInsertTransaction.executeUpdate();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
