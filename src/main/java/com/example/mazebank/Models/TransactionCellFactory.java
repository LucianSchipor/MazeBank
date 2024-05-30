package com.example.mazebank.Models;

import com.example.mazebank.Controllers.User.TransactionListCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TransactionCellFactory implements Callback<ListView<Transaction>, ListCell<Transaction>> {

    @Override
    public ListCell<Transaction> call(ListView<Transaction> param) {
        return new TransactionListCell();
    }
}
