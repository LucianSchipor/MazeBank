package com.example.mazebank.Repositories.BankInfo;

import com.example.mazebank.Core.Bank.BankInfo;
import com.example.mazebank.Core.Credit.Credit;
import javafx.scene.control.Alert;

import java.sql.*;

public class DB_BankInfo {

    public static void GetBankInfo() {
        PreparedStatement querry;
        ResultSet resultSet;
        Credit bankInfo = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            querry = connection.prepareStatement("SELECT * FROM bank_info WHERE info_id");

            resultSet = querry.executeQuery();
            while (resultSet.next()) {
                float intrest = resultSet.getFloat("current_intrest");
                BankInfo.getInstance().setIntrest(intrest);
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
    }
}
