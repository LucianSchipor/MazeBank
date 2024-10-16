package com.example.mazebank.Controllers.User.Transactions;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview = new ListView();
    public TextField payee_fld = new TextField();
    public TextField amount_fld = new TextField();
    public TextArea message_fld = new TextArea();
    public Button send_money_btn = new Button("Send Money");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send_money_btn.setOnAction(this::onSendMoney);
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DB_Transactions.GetBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccountNumber()));
        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }

    public TransactionsController() {
        send_money_btn.setOnAction(this::onSendMoney);
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DB_Transactions.GetBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccountNumber()));
        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }

    private void onSendMoney(Event event) {
        if (!Objects.equals(payee_fld.getText(), "") && !Objects.equals(amount_fld.getText(), "")) {
            DB_Transactions.Transfer(payee_fld.getText(), Double.parseDouble(amount_fld.getText()), message_fld.getText());
            updatePage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("[LOG] - one field was null");
            alert.setContentText("You cannot have empty fields!");
            alert.showAndWait();
        }
    }

    private void updatePage() {
        var account = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();
        var transactionsList = account.getTransactions();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();
        try {
            transactionsList = transactionsList.stream().toList();
            observableTransactionList.addAll(transactionsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        transactions_listview.itemsProperty().set(observableTransactionList);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
        double income = 0;
        double outcome = 0;
        for (Transaction transaction : transactionsList) {
            if (Objects.equals(transaction.getSender(), account.getAccount_id())) {
                outcome += transaction.getAmount();
            } else {
                income += transaction.getAmount();
            }
        }
    }
}




