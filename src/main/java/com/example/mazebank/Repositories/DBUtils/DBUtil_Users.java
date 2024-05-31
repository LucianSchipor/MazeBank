package com.example.mazebank.Repositories.DBUtils;
import com.example.mazebank.Core.Models.CheckingAccount;
import com.example.mazebank.Core.Models.Transaction;
import com.example.mazebank.Core.Models.User;
import javafx.event.Event;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DBUtil_Users {

    public static CheckingAccount getUserAccount(Event event, int user_id){
        Connection connection;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");
            psCheckUserExists.setInt(1, user_id);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("account_balance");
                String currency = resultSet.getString("account_currency");
                return new CheckingAccount("Unknown", balance, currency);
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
        return null;
    }

    public static List<Transaction> getUserTransactions(int from_account_id){
        Connection connection;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT \n" +
                    "    t.transaction_id,\n" +
                    "    t.from_account_id,\n" +
                    "    u1.username AS from_username,\n" +
                    "    t.to_account_id,\n" +
                    "    u2.username AS to_username,\n" +
                    "    t.amount\n" +
                    "FROM \n" +
                    "    transactions t\n" +
                    "JOIN \n" +
                    "    users u1 ON t.from_account_id = u1.user_id\n" +
                    "JOIN \n" +
                    "    users u2 ON t.to_account_id = u2.user_id\n" +
                    "WHERE \n" +
                    "    t.to_account_id = ?");
            psCheckUserExists.setInt(1, from_account_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                transactions.add(new Transaction(transaction_id, fromAccountId, toAccountId, amount, from_username, to_username));
            }
            psCheckUserExists = connection.prepareStatement("SELECT \n" +
                    "    t.transaction_id,\n" +
                    "    t.from_account_id,\n" +
                    "    u1.username AS from_username,\n" +
                    "    t.to_account_id,\n" +
                    "    u2.username AS to_username,\n" +
                    "    t.amount\n" +
                    "FROM \n" +
                    "    transactions t\n" +
                    "JOIN \n" +
                    "    users u1 ON t.from_account_id = u1.user_id\n" +
                    "JOIN \n" +
                    "    users u2 ON t.to_account_id = u2.user_id\n" +
                    "WHERE \n" +
                    "    t.from_account_id = ?");
            psCheckUserExists.setInt(1, from_account_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int transaction_id = resultSet.getInt("transaction_id");
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");
                String from_username = resultSet.getString("from_username");
                String to_username = resultSet.getString("to_username");
                transactions.add(new Transaction(transaction_id, fromAccountId, toAccountId, amount, from_username, to_username));
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                if(t1.getTransaction_id() < t2.getTransaction_id())
                    return 1;
                else
                    if(t1.getTransaction_id() == t2.getTransaction_id())
                        return 0;
                    else
                        return -1;
            }
        });
        return transactions;
    }
    public static User loginUser(Event event, String username, String password){
        Connection connection;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT  * FROM users WHERE username = ? AND password = ?");
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.next()){
                System.out.println("User already exists.");
                int role = resultSet.getInt("role");
                int user_id = resultSet.getInt("user_id");
                return new User(user_id, username, password, role);
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
    return null;
    }
    public static void singInUser(Event event, String username, String password){
        Connection connection;
        PreparedStatement psInsert;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psCheckUserExists = connection.prepareStatement("SELECT  * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                //check if result set is empty
                System.out.println("User already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User already exists");
            }
            else{
                psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
        System.out.println("Inserted!");
    }
}
