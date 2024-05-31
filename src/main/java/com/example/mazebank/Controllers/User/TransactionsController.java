package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.Transaction;
import com.example.mazebank.Repositories.DBUtils.DBUtil_Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DBUtil_Users.getUserTransactions(UserLoggedIn.getInstance().getLoggedInUser().getUserId()));
        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }
}
