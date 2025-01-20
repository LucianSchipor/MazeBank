package com.example.mazebank.Controllers.User.Transactions.TransactionsWithOnePers;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Controllers.User.Transactions.TransactionsController;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsWithOnePersController implements Initializable {
    public ListView<Transaction> transactions_listview = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(
                DB_Transactions.getBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().
                        getSelectedCheckingAccount()
                        .getIBAN()));

        FilteredList<Transaction> filteredTransactionsSender = new FilteredList<>(transactions_observable, transaction -> {
            return transaction.getSender().equals(UserLoggedIn.getInstance().getRetransferSelectedTransaction().getSender());
        });

        FilteredList<Transaction> filteredTransactionsReceiver = new FilteredList<>(transactions_observable, transaction -> {
            return transaction.getReceiver().equals(UserLoggedIn.getInstance().getRetransferSelectedTransaction().getReceiver());
        });

        ObservableList<Transaction> transactionsObservable = FXCollections.observableArrayList();

        transactionsObservable.addAll(filteredTransactionsSender);
        transactionsObservable.addAll(filteredTransactionsReceiver);

        transactionsObservable = FXCollections.observableArrayList(transactionsObservable.stream()
                .distinct()
                .toList());

        transactions_listview.setItems(transactionsObservable);
        transactions_listview.setCellFactory(param -> new TransactionsWithOnePerson_ListController());
    }


}
