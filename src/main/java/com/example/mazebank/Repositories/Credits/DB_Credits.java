package com.example.mazebank.Repositories.Credits;

import com.example.mazebank.Core.Credit.Credit;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import javafx.scene.control.Alert;

import java.security.Key;
import java.security.KeyManagementException;
import java.sql.*;

public class DB_Credits {

    public static Credit getCreditByUserId(int user_id) {
        PreparedStatement querry;
        ResultSet resultSet;
        Credit newCredit = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            querry = connection.prepareStatement("SELECT * FROM credits WHERE user_id = ?");

            querry.setInt(1, user_id);
            resultSet = querry.executeQuery();
            while (resultSet.next()) {
                int credit_id = resultSet.getInt("credit_id");
                float credit_total_sum = Float.parseFloat(EncryptionManager.decrypt(resultSet.getString("credit_total_sum"), KeyManager.loadKey()));
                int credit_period = Integer.parseInt(EncryptionManager.decrypt(resultSet.getString("credit_period"), KeyManager.loadKey()));
                String credit_currency = EncryptionManager.decrypt(resultSet.getString("credit_currency"), KeyManager.loadKey());
                float credit_monthly_rate = Float.parseFloat(EncryptionManager.decrypt(resultSet.getString("credit_monthly_rate"), KeyManager.loadKey()));
                float credit_intrest = Float.parseFloat(EncryptionManager.decrypt(resultSet.getString("credit_intrest"), KeyManager.loadKey()));
                newCredit = new Credit(credit_id, credit_total_sum, credit_period, credit_currency, credit_monthly_rate, credit_intrest);
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        }
        return newCredit;
    }

    public static void createCredit(Credit credit) {
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            querry = connection.prepareStatement("INSERT INTO " +
                    "credits (credit_total_sum, credit_period, credit_currency, credit_monthly_rate, credit_interest, user_id) " +
                    "VALUES (?,?,?,?,?,?)");

            querry.setString(1, EncryptionManager.encrypt(String.valueOf(credit.getCredit_total_sum()), KeyManager.loadKey()));
            querry.setString(2, EncryptionManager.encrypt(String.valueOf(credit.getCredit_period()), KeyManager.loadKey()));
            querry.setString(3, EncryptionManager.encrypt(credit.getCredit_currency(), KeyManager.loadKey()));
            querry.setString(4, EncryptionManager.encrypt(String.valueOf(credit.getCredit_monthly_rate()), KeyManager.loadKey()));
            querry.setString(5, EncryptionManager.encrypt(String.valueOf(credit.getCredit_intrest()), KeyManager.loadKey()));
            querry.setInt(6, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            querry.executeUpdate();

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        }
    }
}
