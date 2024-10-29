package com.example.mazebank.Repositories.Users;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.event.Event;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            return !resultSet.next();
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
                assert connection != null;
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
                DB_BankAccounts.CreateBankAccount(added_user_id, "RON");

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
            assert connection != null;
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

    public static List<User> SearchUsers(String username){
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        List<User> usersList = new ArrayList<>();
        try {
            assert connection != null;
            psCheckUserExists =  connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            psCheckUserExists.setString(1, username + "%");
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                var newUser = new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("role"));
                usersList.add(newUser);
                System.out.println("[LOG] - added user " + newUser.getUsername() + " to list of users!");
            }
        }
        catch (Exception exception){
            System.out.println("[LOG] - failed to search users!");
        }
    return usersList;
    }
}
