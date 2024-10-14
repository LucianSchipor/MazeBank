package com.example.mazebank.Repositories;

import com.example.mazebank.Core.Models.User;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.*;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "CallToPrintStackTrace"})
public class DB_Users {

    public static User LoginUser(String username, String password){
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        }
        catch (SQLException e) {
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
