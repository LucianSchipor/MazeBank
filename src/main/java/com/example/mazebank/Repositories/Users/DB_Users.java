package com.example.mazebank.Repositories.Users;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import com.example.mazebank.Core.Security.SecurityManager;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "CallToPrintStackTrace"})
public class DB_Users {

    private static boolean verifyCredentials(String username) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
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

    public static void upgradeAccount(int user_id) {
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            assert connection != null;
            querry = connection.prepareStatement("UPDATE users SET role = ? WHERE user_Id = ?");
            querry.setInt(1, AccountType.CLIENT.ordinal());
            querry.setInt(2, user_id);
            querry.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB_BankAccounts.createBankAccount(user_id, "RON");
        }
    }

    public static void signupUser(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Username or Password are Required");
            alert.showAndWait();
            throw new Exception("Username or Password are Required");
        }
        PreparedStatement psCheckUserExists;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (verifyCredentials(username)) {
            try {
                assert connection != null;
                psCheckUserExists = connection.prepareStatement("INSERT INTO users (username, password, role, 2FA_Key, email, 2FA_Verification_Time) VALUES (?, ?, ?, ?, ?, ?)");
                psCheckUserExists.setString(1, EncryptionManager.encrypt(username, KeyManager.loadKey()));
                psCheckUserExists.setString(2, EncryptionManager.encrypt(password, KeyManager.loadKey()));
                psCheckUserExists.setInt(3, 2);
                psCheckUserExists.setString(4, EncryptionManager.encrypt("NaN", KeyManager.loadKey()));
                psCheckUserExists.setString(5, EncryptionManager.encrypt(username, KeyManager.loadKey()));
                psCheckUserExists.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now().minusYears(1)));
                psCheckUserExists.executeUpdate();

                psCheckUserExists = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
                psCheckUserExists.setString(1, EncryptionManager.encrypt(username, KeyManager.loadKey()));
                psCheckUserExists.executeQuery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User" + username + " already exists");
            alert.showAndWait();
            System.out.println("[LOG] - used username on register");
            throw new Exception("User\" + username + \" already exists");

        }
    }

    public static void update2FAKey(User user, String key) {
        PreparedStatement psCheckUserExists;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (UserLoggedIn.getInstance().getLoggedInUser() != null) {
            try {
                assert connection != null;
                psCheckUserExists = connection.prepareStatement("UPDATE users SET 2FA_Key = ? WHERE username = ?");
                psCheckUserExists.setString(1, EncryptionManager.encrypt(key, KeyManager.loadKey()));
                psCheckUserExists.setString(2, EncryptionManager.encrypt(user.getUsername(), KeyManager.loadKey()));
                psCheckUserExists.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
            System.out.println("[LOG] - no logged in user");
        }
    }

    public static void updateFAVerificationTime(User user) {
        PreparedStatement psCheckUserExists;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("UPDATE users " +
                    "SET 2FA_Verification_Time = ? " +
                    "WHERE username = ?");
            psCheckUserExists.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            psCheckUserExists.setString(2, EncryptionManager.encrypt(user.getUsername(), KeyManager.loadKey()));
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

    public static User loginUser(String username, String password) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT  * FROM users WHERE username = ? AND password = ?");
            psCheckUserExists.setString(1, EncryptionManager.encrypt(username, KeyManager.loadKey()));
            psCheckUserExists.setString(2, EncryptionManager.encrypt(password, KeyManager.loadKey()));
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.next()) {
                //TODO -> de decriptat
                int role = resultSet.getInt("role");
                int user_id = resultSet.getInt("user_id");
                String email = EncryptionManager.decrypt(resultSet.getString("email"), KeyManager.loadKey()) ;
                String Key =  EncryptionManager.decrypt(resultSet.getString("2FA_Key"), KeyManager.loadKey());
                Timestamp timestamp = resultSet.getTimestamp("2FA_Verification_Time");
                boolean FA_Enabled = !Key.equals("NaN") && !Key.isEmpty();
                LocalDateTime FA_Verification_Time = null;
                if (timestamp != null) {
                    FA_Verification_Time = timestamp.toLocalDateTime();
                }
                var newUser = new User(user_id, username, password, role, email);
                SecurityManager.getInstance().setFA_Enabled(FA_Enabled);
                SecurityManager.getInstance().setFA_Key(Key);
                SecurityManager.getInstance().setFA_Verification_Time(FA_Verification_Time);
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

    public static List<User> searchUsers(String username) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        List<User> usersList = new ArrayList<>();
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            psCheckUserExists.setString(1,  EncryptionManager.encrypt(username, KeyManager.loadKey()) + "%");
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

    public static User searchUserById(int user_id) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            psCheckUserExists.setInt(1,  user_id);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("role"), resultSet.getString("email"));
            }
        } catch (Exception exception) {
            System.out.println("[LOG] - failed to search users!");
        }
        return null;
    }
}
