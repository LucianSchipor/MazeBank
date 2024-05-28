package com.example.mazebank.Models.DBUtils;

import com.example.mazebank.Controllers.User.UserLoggedIn;
import com.example.mazebank.Models.Account;
import com.example.mazebank.Models.CheckingAccount;
import com.example.mazebank.Models.Client;
import com.example.mazebank.Models.User;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil_Users {

    public static CheckingAccount getUserAccount(Event event, int user_id){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            String querry = "SELECT * FROM bank_accounts WHERE user_id = ?";
            psCheckUserExists = connection.prepareStatement(querry);
            psCheckUserExists.setInt(1, user_id);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("account_balance");
                CheckingAccount userAccount = new CheckingAccount("Unknown", balance, 100);

                return userAccount;
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
        return null;
    }
    public static User loginUser(Event event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
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
                User loggedInUser = new User(user_id, username, password, role);
                var checkingAccount = getUserAccount(event, user_id);
                return loggedInUser;
            }
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        }
    return null;
    }
    public static void singInUser(Event event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
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
