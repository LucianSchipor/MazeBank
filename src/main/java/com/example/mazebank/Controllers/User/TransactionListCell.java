package com.example.mazebank.Controllers.User;

import com.example.mazebank.Models.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class TransactionListCell extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setText(null);
        } else {
            setText("Transaction_Id: " + transaction.getTransaction_id() +
                    ", From: " + transaction.getFrom_username() +
                    ", To: " + transaction.getTo_username() +
                    ", Amount: " + transaction.getAmount());
            if(transaction.getFrom_account_id() == UserLoggedIn.getInstance().getLoggedInUser().getUserId())
            setStyle("-fx-background-color: #f57767;");
            else
                setStyle("-fx-background-color: #81f567;");
        }
    }
}
