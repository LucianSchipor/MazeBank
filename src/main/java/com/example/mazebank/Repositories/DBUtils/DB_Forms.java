package com.example.mazebank.Repositories.DBUtils;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB_Forms {

    public static List<Form> GetForms() {
        PreparedStatement psCheckUserExists;
        List<Form> forms = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT * FROM forms");
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                int form_id = resultSet.getInt("form_id");
                String path = resultSet.getString("form_path");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                String status = resultSet.getString("status");
                Form form = new Form(form_id, path, resultSet.getDate("date"), fName, lName, status);
                forms.add(form);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return forms;
    }
}
