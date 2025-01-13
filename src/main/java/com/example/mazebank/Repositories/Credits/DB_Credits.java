package com.example.mazebank.Repositories.Credits;

import com.example.mazebank.Core.Credit.Credit;
import javafx.scene.control.Alert;

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
                float credit_total_sum = resultSet.getFloat("credit_total_sum");
                int credit_period = resultSet.getInt("credit_period");
                String credit_currency = resultSet.getString("credit_currency");
                float credit_monthly_rate = resultSet.getFloat("credit_monthly_rate");
                float credit_intrest = resultSet.getFloat("credit_intrest");
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
            querry = connection.prepareStatement("INSERT INTO credits VALUES (?,?,?,?,?,?,?)");

            querry.setInt(1, credit.getCredit_id());
            querry.setFloat(2, credit.getCredit_total_sum());
            querry.setInt(3, credit.getCredit_period());
            querry.setString(4, credit.getCredit_currency());
            querry.setFloat(5, credit.getCredit_monthly_rate());
            querry.setFloat(6, credit.getCredit_intrest());
            querry.setInt(7, credit.getCredit_id());
            querry.executeUpdate();

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
    }
}
