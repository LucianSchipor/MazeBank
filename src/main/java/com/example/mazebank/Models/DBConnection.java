//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.mazebank.Models;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection c;
    public Statement s;

    public static void singUnUser(Event event, String username, String password){
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
