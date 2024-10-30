package com.example.mazebank.Repositories.Transactions;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection", "CallToPrintStackTrace"})
public class DB_Transactions {

    //TODO -> de facut conversia (ex: Trimit 150 ron si ar trebui sa ajunga 30 EUR, nu 150 EUR).

    private static boolean VerifyTransfer(String receiver, String amount_String, String message) {
        if (receiver.isBlank() || amount_String.isBlank() || message.isBlank()) {
            return false;
        }
        for (Map.Entry<String, BankAccount> entry : UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet()) {
            String account_number = entry.getKey();
            if(account_number.equals(receiver)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot send money to your own account. Use Deposit.");
                alert.showAndWait();
                return false;
            }
        }

        if (Objects.equals(receiver, UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot send money to your own account!");
            alert.showAndWait();
            return false;
        }
        double amount;
        try {
            amount = Float.parseFloat(amount_String);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("IBAN format is incorrect!");
            System.out.println("[LOG] - IBAN format is incorrect!");
            alert.showAndWait();
            return false;
        }
        if (receiver.isEmpty() || amount == 0 || amount < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("[LOG] - receiver field from Transfer has an incorrect type or value!");
            alert.setContentText("Receiver field from Transfer has an incorrect type or value!");
            alert.showAndWait();
            return false;
        }
        if (UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getBalance() < amount) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You do not have that balance!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //Database Statement
    public static void Transfer(String receiver, Double amount, String message) {
        if (!VerifyTransfer(receiver, amount.toString(), message)) {
            return;
        }
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PreparedStatement psInsertTransaction;
        try {
            assert connection != null;
            psInsertTransaction = connection.prepareStatement(
                    "INSERT INTO" +
                            " transactions (sender,receiver,amount, message, currency) VALUES (?,?,?,?,?)");

            psInsertTransaction.setString(1, UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id());
            psInsertTransaction.setString(2, receiver);
            psInsertTransaction.setDouble(3, amount);
            psInsertTransaction.setString(4, message);
            psInsertTransaction.setString(5, UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getCurrency());
            try {
                int rowsAffected = psInsertTransaction.executeUpdate();
                if (rowsAffected > 0) {
                    DB_BankAccounts.DB_UpdateBankAccountsAfterTransaction(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id(), amount, receiver);
                    DB_BankAccounts.Local_UpdateBankAccountsAfterTransaction(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount());
                    System.out.println("Transaction successfully inserted.");
                } else {
                    System.out.println("Failed to insert transaction.");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bank account with IBAN " + receiver + " does not exists!");
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


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Transfer successful!");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("UseCompareMethod")
    public static List<Transaction> GetBankAccountTransactions(String bank_account_number) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
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
                        u1.username AS from_username,
                        t.receiver,
                        u2.username AS to_username,
                        t.amount
                    FROM
                        transactions t
                    JOIN
                    	bank_accounts b1 ON t.sender = b1.account_id
                    JOIN
                    	bank_accounts b2 On t.receiver = b2.account_id
                    JOIN
                        users u1 ON b1.user_id = u1.user_id
                    JOIN
                        users u2 ON b2.user_id = u2.user_id
                    WHERE
                        t.receiver = ?
                    """);
            psCheckUserExists.setString(1, bank_account_number);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
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
                var transaction = new Transaction(transaction_id, sender, receiver, amount, from_username, to_username, message);
                transaction.setCurrency(currency);
                transactions.add(transaction);
            }
            psCheckUserExists = connection.prepareStatement("""
                    SELECT
                                                 t.transaction_id,
                                                 t.sender,
                                                 t.message,
                                                 t.currency,
                                                 u1.username AS from_username,
                                                 t.receiver,
                                                 u2.username AS to_username,
                                                 t.amount
                                             FROM
                                                 transactions t
                                             JOIN
                                             	bank_accounts b1 ON t.sender = b1.account_id
                                             JOIN
                                             	bank_accounts b2 On t.receiver = b2.account_id
                                             JOIN
                                                 users u1 ON b1.user_id = u1.user_id
                                             JOIN
                                                 users u2 ON b2.user_id = u2.user_id
                                             WHERE
                                                 t.sender = ?;""");
            psCheckUserExists.setString(1, bank_account_number);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
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
                var transaction = new Transaction(transaction_id, sender, receiver, amount, from_username, to_username, message);
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
