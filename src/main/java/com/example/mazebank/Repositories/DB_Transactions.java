package com.example.mazebank.Repositories;

import com.example.mazebank.Controllers.User.UserLoggedIn;
import com.example.mazebank.Core.Models.Transaction;
import com.example.mazebank.Repositories.DBUtils.DB_BankAccounts;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection", "CallToPrintStackTrace"})
public class DB_Transactions {

    //TODO -> de facut conversia (ex: Trimit 150 ron si ar trebui sa ajunga 30 EUR, nu 150 EUR).
    public static void TransferMoney(String to_account_id_String, String amount_String, String message){
        if(Integer.parseInt(to_account_id_String) ==
        UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot send money to selected account!");
            alert.showAndWait();
            return;
        }
        double amount;
        try {
            amount = Integer.parseInt(amount_String);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Id format is incorrect!");
            System.out.println("Id format is incorrect!");
            alert.showAndWait();
            return;
        }
        if(to_account_id_String.isEmpty() || amount == 0 || amount < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect value on payee field or amount field!");
            alert.showAndWait();
            return;
        }
        if(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getBalance() < amount){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You do not have that balance!");
            alert.showAndWait();
            return;
        }
        TransferMoney_DatabaseStatement(to_account_id_String, amount, message);

    }
    //Database Statement
    private static void TransferMoney_DatabaseStatement(String to_account_id_String, Double amount, String message){
        Connection connection = null;
        PreparedStatement psInsertTransaction;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psInsertTransaction = connection.prepareStatement(
                    "INSERT INTO" +
                            " transactions (from_account_id,to_account_id,amount, message) VALUES (?,?,?,?)");

            psInsertTransaction.setInt(1, UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id());
            psInsertTransaction.setInt(2, Integer.parseInt(to_account_id_String));
            psInsertTransaction.setDouble(3, amount);
            psInsertTransaction.setString(4, message);
            try{
                int rowsAffected = psInsertTransaction.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Transaction successfully inserted.");
                } else {
                    System.out.println("Failed to insert transaction.");
                }
                UserLoggedIn.getInstance().getLoggedInUser().setSelectedCheckingAccount(
                        DB_BankAccounts.UpdateBankAccount_Local(
                                UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount()));
            }
            catch (SQLIntegrityConstraintViolationException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bank account with id " + to_account_id_String + " does not exists!");
                alert.showAndWait();
                return;
            }
            psInsertTransaction.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Transfer successful!");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("UseCompareMethod")
    public static List<Transaction> GetBankAccountTransactions(int from_account_id) {
        Connection connection = null;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        List<Transaction> transactions = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("""
                     SELECT
                        t.transaction_id,
                        t.from_account_id,
                        t.message,
                        t.currency,
                        u1.username AS from_username,
                        t.to_account_id,
                        u2.username AS to_username,
                        t.amount
                    FROM
                        transactions t
                    JOIN
                    	bank_accounts b1 ON t.from_account_id = b1.account_id
                    JOIN
                    	bank_accounts b2 On t.to_account_id = b2.account_id
                    JOIN
                        users u1 ON b1.user_id = u1.user_id
                    JOIN
                        users u2 ON b2.user_id = u2.user_id
                    WHERE
                        t.to_account_id = ?
                    """);
            psCheckUserExists.setInt(1, from_account_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                String currency = resultSet.getString("currency");
                String message = "";
                try {
                    message = resultSet.getString("message");
                } catch (Exception e) {
                    System.out.println("Message is null");
                }
                var transaction = new Transaction(transaction_id, fromAccountId, toAccountId, amount, from_username, to_username, message);
                transaction.setCurrency(currency);
                transactions.add(transaction);
            }
            psCheckUserExists = connection.prepareStatement("""
                    SELECT
                                                 t.transaction_id,
                                                 t.from_account_id,
                                                 t.message,
                                                 t.currency,
                                                 u1.username AS from_username,
                                                 t.to_account_id,
                                                 u2.username AS to_username,
                                                 t.amount
                                             FROM
                                                 transactions t
                                             JOIN
                                             	bank_accounts b1 ON t.from_account_id = b1.account_id
                                             JOIN
                                             	bank_accounts b2 On t.to_account_id = b2.account_id
                                             JOIN
                                                 users u1 ON b1.user_id = u1.user_id
                                             JOIN
                                                 users u2 ON b2.user_id = u2.user_id
                                             WHERE
                                                 t.from_account_id = ?;""");
            psCheckUserExists.setInt(1, from_account_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                String currency = resultSet.getString("currency");
                String message = "";
                try {
                    message = resultSet.getString("message");
                } catch (Exception e) {
                    System.out.println("Message is null");
                }
                var transaction = new Transaction(transaction_id, fromAccountId, toAccountId, amount, from_username, to_username, message);
                transaction.setCurrency(currency);
                transactions.add(transaction);
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        transactions.sort((t1, t2) -> {
            if (t1.getTransaction_id() < t2.getTransaction_id())
                return 1;
            else if (t1.getTransaction_id() == t2.getTransaction_id())
                return 0;
            else
                return -1;
        });
        return transactions;
    }
}
