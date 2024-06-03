package com.example.mazebank.Repositories;

import com.example.mazebank.Controllers.User.UserLoggedIn;
import com.example.mazebank.Core.Models.User;
import javafx.scene.control.Alert;

import java.sql.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection", "CallToPrintStackTrace"})
public class DB_Transactions {

    public static void transferMoneyToAccount(String to_user_id_String, String amount_String, String message){
        double amount;
        try {
            amount = Integer.parseInt(amount_String);
            boolean hasDigits = to_user_id_String.matches(".*\\d.*");
            if(hasDigits){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Id cannot have digits!");
                alert.showAndWait();
                return;
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Id format is incorrect!");
            System.out.println("Id format is incorrect!");
            alert.showAndWait();
            return;
        }
        if(to_user_id_String == "" || amount == 0 || amount < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect value on payee field or amount field!");
            alert.showAndWait();
            return;
        }
        if(to_user_id_String == UserLoggedIn.getInstance().getLoggedInUser().getUsername())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot send money to yourself!");
            alert.showAndWait();
            return;
        }
        if(UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccount().getBalance() < amount){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You do not have that balance!");
            alert.showAndWait();
            return;
        }
        Connection connection = null;
        PreparedStatement psInsertTransaction;
        PreparedStatement psSelectUserByUsername;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psInsertTransaction = connection.prepareStatement(
                    "INSERT INTO" +
                    " transactions (from_account_id,to_account_id,amount, message) VALUES (?,?,?,?)");
            psSelectUserByUsername = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            int to_user_id = 0;
            psSelectUserByUsername.setString(1, to_user_id_String);
            var result = psSelectUserByUsername.executeQuery();
            if(result.next()){
                to_user_id = result.getInt("user_id");
            }
            psInsertTransaction.setInt(1, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            psInsertTransaction.setInt(2, to_user_id);
            psInsertTransaction.setDouble(3, amount);
            psInsertTransaction.setString(4, message);
           try{
               int rowsAffected = psInsertTransaction.executeUpdate();
               if (rowsAffected > 0) {
                   System.out.println("Transaction successfully inserted.");
               } else {
                   System.out.println("Failed to insert transaction.");
               }
           }
           catch (SQLIntegrityConstraintViolationException e){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("User with id " + to_user_id + " does not exists!");
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
        }
    }
}
