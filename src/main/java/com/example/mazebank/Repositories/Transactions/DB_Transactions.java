package com.example.mazebank.Repositories.Transactions;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection", "CallToPrintStackTrace"})
public class DB_Transactions {

    //TODO -> de facut conversia (ex: Trimit 150 ron si ar trebui sa ajunga 30 EUR, nu 150 EUR).

    //Database Statement
    public static void transfer(String receiver, Double amount, String message) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PreparedStatement psInsertTransaction;
        try {
            if (DB_BankAccounts.searchBankAccountByIBAN(receiver) != null) {
                try {
                    assert connection != null;
                    connection = DB_ConnectionManager.getInstance().getConnection();
                    psInsertTransaction = connection.prepareStatement(
                            "INSERT INTO" +
                                    " transactions (sender,receiver,amount, message, currency, datetime) VALUES (?,?,?,?,?,?)");

                    psInsertTransaction.setString(1, EncryptionManager.encrypt(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getIBAN(), KeyManager.loadKey()));
                    psInsertTransaction.setString(2, EncryptionManager.encrypt(receiver, KeyManager.loadKey()));
                    psInsertTransaction.setString(3, EncryptionManager.encrypt(amount.toString(), KeyManager.loadKey()));
                    psInsertTransaction.setString(4, EncryptionManager.encrypt(message, KeyManager.loadKey()));
                    psInsertTransaction.setString(5, EncryptionManager.encrypt(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getCurrency(), KeyManager.loadKey()));
                    psInsertTransaction.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                    int rowsAffected = psInsertTransaction.executeUpdate();
                    if (rowsAffected > 0) {
                        DB_BankAccounts.updateBankAccountsAfterTransaction(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getIBAN(), amount, receiver);
                        System.out.println("Transaction successfully inserted.");
                    } else {
                        System.out.println("Failed to insert transaction.");
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Bank account with IBAN " + receiver + " does not exists!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Success!");
                    alert.setTitle("Success!");
                    alert.setContentText("Transfer successful!");
                    alert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @SuppressWarnings("UseCompareMethod")
    public static List<Transaction> getBankAccountTransactions(String IBAN) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        List<Transaction> transactions = new ArrayList<>();
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("""
                     SELECT
                        t.transaction_id,
                        t.sender,
                        t.message,
                        t.currency,
                        t.datetime,
                        u1.username AS from_username,
                        t.receiver,
                        u2.username AS to_username,
                        t.amount
                    FROM
                        transactions t
                    JOIN
                    	bank_accounts b1 ON t.sender = b1.iban
                    JOIN
                    	bank_accounts b2 On t.receiver = b2.iban
                    JOIN
                        users u1 ON b1.user_id = u1.user_id
                    JOIN
                        users u2 ON b2.user_id = u2.user_id
                    WHERE
                        t.receiver = ?
                    """);
            psCheckUserExists.setString(1, EncryptionManager.encrypt(IBAN, KeyManager.loadKey()));
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                String sender = EncryptionManager.decrypt(resultSet.getString("sender"), KeyManager.loadKey());
                String receiver = EncryptionManager.decrypt(resultSet.getString("receiver"), KeyManager.loadKey());
                double amount = Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("amount"), KeyManager.loadKey()));
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                String currency = EncryptionManager.decrypt(resultSet.getString("currency"), KeyManager.loadKey());
                Timestamp date = resultSet.getTimestamp("datetime");
                String message = "";
                BankAccount sender_BAcc = DB_BankAccounts.searchBankAccountByIBAN(sender);
                BankAccount receiver_BAcc = DB_BankAccounts.searchBankAccountByIBAN(receiver);
                try {
                    message = resultSet.getString("message");
                } catch (Exception e) {
                    System.out.println("Message is null");
                }
                var transaction = new Transaction(transaction_id, sender, receiver, amount, from_username, to_username, message);
                transaction.setCurrency(currency);
                transaction.setReciever_BankAccount(receiver_BAcc);
                transaction.setSender_BankAccount(sender_BAcc);
                transaction.setTimestamp(date);
                transactions.add(transaction);

            }
            psCheckUserExists = connection.prepareStatement("""
                    SELECT
                                                 t.transaction_id,
                                                 t.sender,
                                                 t.message,
                                                 t.currency,
                                                 t.datetime,
                                                 u1.username AS from_username,
                                                 t.receiver,
                                                 u2.username AS to_username,
                                                 t.amount
                                             FROM
                                                 transactions t
                                             JOIN
                                             	bank_accounts b1 ON t.sender = b1.iban
                                             JOIN
                                             	bank_accounts b2 On t.receiver = b2.iban
                                             JOIN
                                                 users u1 ON b1.user_id = u1.user_id
                                             JOIN
                                                 users u2 ON b2.user_id = u2.user_id
                                             WHERE
                                                 t.sender = ?;""");
            psCheckUserExists.setString(1, EncryptionManager.encrypt(IBAN, KeyManager.loadKey()));
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                String sender = EncryptionManager.decrypt(resultSet.getString("sender"), KeyManager.loadKey());
                String receiver = EncryptionManager.decrypt(resultSet.getString("receiver"), KeyManager.loadKey());
                double amount = Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("amount"), KeyManager.loadKey()));
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                String currency = EncryptionManager.decrypt(resultSet.getString("currency"), KeyManager.loadKey());
                Timestamp date = resultSet.getTimestamp("datetime");
                String message = "";
                BankAccount sender_BAcc = DB_BankAccounts.searchBankAccountByIBAN(sender);
                BankAccount receiver_BAcc = DB_BankAccounts.searchBankAccountByIBAN(receiver);
                try {
                    message = EncryptionManager.decrypt(resultSet.getString("message"), KeyManager.loadKey());
                } catch (Exception e) {
                    System.out.println("Message is null");
                }
                var transaction = new Transaction(transaction_id, sender, receiver, amount, from_username, to_username, message);
                transaction.setCurrency(currency);
                transaction.setReciever_BankAccount(receiver_BAcc);
                transaction.setSender_BankAccount(sender_BAcc);
                transaction.setTimestamp(date);
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
