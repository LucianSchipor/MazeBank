package com.example.mazebank.Repositories.BankInfo;
import com.example.mazebank.Core.Bank.BankInfo;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import javafx.scene.control.Alert;

import java.security.Key;
import java.sql.*;

public class DB_BankInfo {

    public static void getBankInfo() {
        PreparedStatement querry;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            querry = connection.prepareStatement("SELECT * FROM bank_info WHERE info_id");
            resultSet = querry.executeQuery();
            if(resultSet.next()){
            }
            while (resultSet.next()) {
                var interest = Float.valueOf(EncryptionManager.decrypt(resultSet.getString("info_id"), KeyManager.loadKey()));
                BankInfo.getInstance().setIntrest(interest);
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
