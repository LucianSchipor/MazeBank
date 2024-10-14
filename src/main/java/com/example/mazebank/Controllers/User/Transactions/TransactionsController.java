package com.example.mazebank.Controllers.User.Transactions;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview = new ListView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DB_Transactions.GetBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next().getKey()));
        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }

    public TransactionsController(){
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DB_Transactions.GetBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next().getKey()));
        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }
}
