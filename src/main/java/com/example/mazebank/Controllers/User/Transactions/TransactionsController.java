package com.example.mazebank.Controllers.User.Transactions;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.SecurityManager;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactions_listview = new ListView<>();
    public TextField payee_fld = new TextField();
    public TextField amount_fld = new TextField();
    public TextArea message_fld = new TextArea();
    public Button send_money_btn = new Button("Send Money");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send_money_btn.setOnAction(event -> {
            try {
                onSendMoney();
            } catch (Exception e) {
                System.out.println("[LOG][Transactions][Cause] - " + e.getCause());
                System.out.println("[LOG][Transactions][Message] - " + e.getMessage());
            }
        });
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(
                DB_Transactions.getBankAccountTransactions(UserLoggedIn.getInstance().getLoggedInUser().
                        getSelectedCheckingAccount()
                        .getIBAN()));

        Platform.runLater(() -> {
            transactions_listview.setItems(transactions_observable);
            transactions_listview.setCellFactory(param -> new TransactionListCell());
        });

    }

    public TransactionsController() {
        send_money_btn.setOnAction(event -> {
            try {
                onSendMoney();
            } catch (Exception e) {
                System.out.println("[LOG][Transactions][Cause] - " + e.getCause());
                System.out.println("[LOG][Transactions][Message] - " + e.getMessage());
            }
        });
        ObservableList<Transaction> transactions_observable = FXCollections.observableArrayList(DB_Transactions.getBankAccountTransactions
                (UserLoggedIn.getInstance().getLoggedInUser().
                        getSelectedCheckingAccount().getIBAN()));

        transactions_listview.setItems(transactions_observable);
        transactions_listview.setCellFactory(param -> new TransactionListCell());
    }

    private void onSendMoney() {
        var selectedAccount = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();
        if (!Objects.equals(payee_fld.getText(), "") && !Objects.equals(amount_fld.getText(), "")) {
            if (SecurityManager.getInstance().isFA_Verified()) {
                if (verifyTransfer(payee_fld.getText(), amount_fld.getText(), message_fld.getText())) {
                    selectedAccount.setBalance(selectedAccount.getBalance() - Double.parseDouble(amount_fld.getText()));
                    DB_Transactions.transfer(payee_fld.getText(), Double.parseDouble(amount_fld.getText()), message_fld.getText());
                    updatePage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    System.out.println("[LOG] - one field was null");
                    alert.setContentText("You cannot have empty fields!");
                    alert.showAndWait();
                }
            } else {
                Model.getInstance().getViewFactory().closeStage((Stage) send_money_btn.getScene().getWindow());
                Model.getInstance().getViewFactory().show2FAWindow(send_money_btn.getScene());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("[LOG] - one field was null");
            alert.setContentText("You cannot have empty fields!");
            alert.showAndWait();
        }
        updatePage();
    }

    private static boolean verifyTransfer(String receiver, String amount_String, String message) {
        if (receiver.isBlank() || amount_String.isBlank() || message.isBlank()) {
            return false;
        }
        for (Map.Entry<String, BankAccount> entry : UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet()) {
            String account_number = entry.getValue().getIBAN();
            if (account_number.equals(receiver)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot send money to your own account. Use Deposit.");
                alert.showAndWait();
                return false;
            }
        }

        if (Objects.equals(receiver, UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getIBAN())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot send money to your own account!");
            alert.showAndWait();
            return false;
        }
        double amount;
        try {
            amount = Float.parseFloat(amount_String);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("IBAN format is incorrect!");
            System.out.println("[LOG] - IBAN format is incorrect!");
            alert.showAndWait();
            return false;
        }
        if (amount == 0 || amount < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("[LOG] - receiver field from Transfer has an incorrect type or value!");
            alert.setContentText("Receiver field from Transfer has an incorrect type or value!");
            alert.showAndWait();
            return false;
        }
        if (UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getBalance() < amount) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You do not have that balance!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void updatePage() {
        UserLoggedIn.getInstance().getLoggedInUser().setSelectedCheckingAccount(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount());
        var account = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();

        account.setTransactions(DB_Transactions.getBankAccountTransactions(account.getIBAN()));
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
    }
}




