package com.example.mazebank.Repositories;

import com.example.mazebank.Controllers.User.UserLoggedIn;
import com.example.mazebank.Core.Models.CheckingAccount;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.*;

public class DB_Transactions {

    public static void transferMoneyToAccount(int to_user_id, double amount){
        Connection connection = null;
        PreparedStatement psInsertTransaction;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
            psInsertTransaction = connection.prepareStatement("" +
                    "INSERT INTO" +
                    " transactions (from_account_id,to_account_id,amount) VALUES (?,?,?)");
            psInsertTransaction.setInt(1, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            psInsertTransaction.setInt(2, to_user_id);
            psInsertTransaction.setDouble(3, amount);
            int rowsAffected = psInsertTransaction.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Transaction successfully inserted.");
            } else {
                System.out.println("Failed to insert transaction.");
            }

            // Închide PreparedStatement-ul
            psInsertTransaction.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Asigură-te că închizi conexiunea la baza de date
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
