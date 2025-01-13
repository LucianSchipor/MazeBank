package com.example.mazebank.Repositories.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_ConnectionManager {
    private static DB_ConnectionManager instance;
    private Connection con;

    public static synchronized DB_ConnectionManager getInstance() {
        if (instance == null) {
            instance = new DB_ConnectionManager();
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
    }

    public Connection getConnection() throws SQLException {
        if(this.con == null || this.con.isClosed())
            this.openConnection();
        return this.con;
    }
}
