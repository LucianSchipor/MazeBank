//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.example.mazebank.Repositories.DBUtils;

import com.example.mazebank.Core.Models.Transaction;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private Connection connection;
    PreparedStatement psCheckUserExists;
    ResultSet resultSet;
    List<Transaction> transactions = new ArrayList<Transaction>();

    public void OpenConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze-bank", "root", "Schiporgabriel20@");
        } catch (SQLException e) {

        }
    }

//    public ResultSet ExecuteQuerry(String query) throws SQLException {
//        var statement = connection.prepareStatement(query);
//    }  //TODO

    public void CloseConnection() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
