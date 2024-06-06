package com.example.mazebank.Repositories.DBUtils;

import com.example.mazebank.Core.Models.CheckingAccount;
import com.example.mazebank.Core.Models.Transaction;
import com.example.mazebank.Core.Models.User;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "CallToPrintStackTrace"})
public class DBUtil_Users {

    @SuppressWarnings("SqlNoDataSourceInspection")
    public static List<CheckingAccount> getUserBankAccount(Event event, int user_id) {
        Connection connection = null;
        PreparedStatement psCheckUserExists;
        PreparedStatement psGetBankAccountTransactions;
        List<CheckingAccount> accounts = new ArrayList<>();
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
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
                newBankAccount.setTransactions(getBankAccountTransactions(account_id));
                accounts.add(newBankAccount);
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

    @SuppressWarnings("UseCompareMethod")
    public static List<Transaction> getBankAccountTransactions(int from_account_id) {
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
                                                 t.from_account_id = ?;
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

    public static User loginUser(Event event, String username, String password) {
        Connection connection = null;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT  * FROM users WHERE username = ? AND password = ?");
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.next()) {
                int role = resultSet.getInt("role");
                int user_id = resultSet.getInt("user_id");
                return new User(user_id, username, password, role);
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
        return null;
    }

    public static void singInUser(Event event, String username, String password) {
        Connection connection = null;
        PreparedStatement psInsert;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT  * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                //check if result set is empty
                System.out.println("User already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User already exists");
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
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
        System.out.println("Inserted!");
    }
}
