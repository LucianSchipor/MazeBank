package com.example.mazebank.Repositories.Users;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.Random;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "CallToPrintStackTrace"})
public class DB_Users {

    public static String GenerateNewAccountNumber() {
        Random random = new Random();

        // Prima cifră nu poate fi 0, deci generăm o cifră între 1 și 9
        StringBuilder number = new StringBuilder();
        number.append(random.nextInt(9) + 1); // Generăm primul număr între 1 și 9

        // Generăm restul de 15 cifre (pot include 0)
        for (int i = 1; i < 16; i++) {
            number.append(random.nextInt(10)); // Generăm număr între 0 și 9
        }

        return number.toString();
    }


    private static boolean VerifyCredentials(String username) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.next())
                return false;
            else
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SignupUser(String username, String password) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (VerifyCredentials(username)) {
            try {
                psCheckUserExists = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                psCheckUserExists.setString(1, username);
                psCheckUserExists.setString(2, password);
                psCheckUserExists.setInt(3, 1);
                psCheckUserExists.executeUpdate();

                psCheckUserExists = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
                psCheckUserExists.setString(1, username);
                resultSet = psCheckUserExists.executeQuery();
                int added_user_id = 0;
                while (resultSet.next()) {
                    added_user_id = resultSet.getInt("user_id");
                }
                psCheckUserExists = connection.prepareStatement("INSERT INTO bank_accounts (account_id, account_balance, account_currency, user_id) VALUES (?,?,?,?)");
                psCheckUserExists.setString(1, GenerateNewAccountNumber());
                psCheckUserExists.setFloat(2, 0);
                psCheckUserExists.setString(3, "RON");
                psCheckUserExists.setInt(4, added_user_id);
                int rowsAffected = psCheckUserExists.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Successfully created user account.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User" + username + " already exists");
            alert.showAndWait();
            System.out.println("[LOG] - used username on register");
        }
    }

    public static User LoginUser(String username, String password) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
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

    public static void SigninUser(Event event, String username, String password) {
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
