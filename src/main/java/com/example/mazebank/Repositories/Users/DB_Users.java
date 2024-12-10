package com.example.mazebank.Repositories.Users;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "CallToPrintStackTrace"})
public class DB_Users {

    public static String GenerateNewAccountNumber() {
        Random random = new Random();

        StringBuilder number = new StringBuilder();
        number.append(random.nextInt(9) + 1);

        for (int i = 1; i < 16; i++) {
            number.append(random.nextInt(10));
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
                psCheckUserExists = connection.prepareStatement("INSERT INTO users (username, password, role, 2FA_Key, email, 2FA_Verification_Time) VALUES (?, ?, ?, ?, ?, ?)");
                psCheckUserExists.setString(1, username);
                psCheckUserExists.setString(2, password);
                psCheckUserExists.setInt(3, 1);
                psCheckUserExists.setString(4, "NaN");
                psCheckUserExists.setString(5, username);
                psCheckUserExists.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now().minusYears(1)));
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

    public static void Update2FAKey(User user, String key) {
        PreparedStatement psCheckUserExists;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (UserLoggedIn.getInstance().getLoggedInUser() != null) {
            try {
                assert connection != null;
                psCheckUserExists = connection.prepareStatement("UPDATE users SET 2FA_Key = ? WHERE username = ?");
                psCheckUserExists.setString(1, key);
                psCheckUserExists.setString(2, user.getUsername());
                psCheckUserExists.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
            System.out.println("[LOG] - no logged in user");
        }
    }


    public static void UpdateFAVerificationTime(User user) {
        PreparedStatement psCheckUserExists;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("UPDATE users " +
                    "SET 2FA_Verification_Time = ? " +
                    "WHERE username = ?");
            psCheckUserExists.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            psCheckUserExists.setString(2, user.getUsername());
            psCheckUserExists.executeUpdate();

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
                String email = resultSet.getString("email");
                String Key = resultSet.getString("2FA_Key");
                Timestamp timestamp = resultSet.getTimestamp("2FA_Verification_Time");
                Boolean FA_Enabled = false;
                if(!Key.equals("NaN") && !Key.isEmpty() && !Key.equals("")) {
                    FA_Enabled = true;
                }
                LocalDateTime FA_Verification_Time = null;
                if(timestamp != null) {
                    FA_Verification_Time = timestamp.toLocalDateTime();
                }
                var newUser = new User(user_id, username, password, role, email);
                Security.getInstance().setFA_Enabled(FA_Enabled);
                Security.getInstance().setFA_Key(Key);
                Security.getInstance().setFA_Verification_Time(FA_Verification_Time);
                return newUser;
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

    public static List<User> SearchUsers(String username) {
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
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            psCheckUserExists.setString(1, username + "%");
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                var newUser = new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("role"), resultSet.getString("email"));
                usersList.add(newUser);
                System.out.println("[LOG] - added user " + newUser.getUsername() + " to list of users!");
            }
        } catch (Exception exception) {
            System.out.println("[LOG] - failed to search users!");
        }
        return usersList;
    }
}
