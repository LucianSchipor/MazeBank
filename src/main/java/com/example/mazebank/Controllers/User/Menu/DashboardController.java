package com.example.mazebank.Controllers.User.Menu;

import com.example.mazebank.Controllers.User.BankAccounts.Cell.AccountListCell;
import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Transactions.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label income_lbl = new Label();
    public Label expense_lbl = new Label();
    public Label selectedAccount_lbl = new Label();
    public ListView<Transaction> transaction_listview = new ListView<>();
    public ListView<BankAccount> account_listview = new ListView<>();
    public Label login_date = new Label();
    public Label balance = new Label();
    public Text hello_lbl = new Text("Hello");
    public Label currency_lbl = new Label();
    public Label acc_selected_number_lbl = new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_date.setText(LocalDate.now().toString());

        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        var account_list = userLoggedIn.getCheckingAccounts();
        Map.Entry<String, BankAccount> entry = UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next();
        var account = entry.getValue();

        if (UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount() != null) {
            account = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();
        }

        var balanceReg = account.getBalance();
        var transactionsList = account.getTransactions();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();
        try {
            transactionsList = transactionsList.stream().limit(5).toList();
            observableTransactionList.addAll(transactionsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ObservableList<BankAccount> observableAccountsList = FXCollections.observableArrayList();
        observableAccountsList.addAll(account_list.values().stream().toList());
        account_listview.itemsProperty().set(observableAccountsList);
        setAccountlistView(account_listview);

        transaction_listview.itemsProperty().set(observableTransactionList);
        transaction_listview.setCellFactory(param -> new TransactionListCell());
        double income = 0;
        double outcome = 0;
        for (Transaction transaction : transactionsList) {
            if (Objects.equals(transaction.getSender(), account.getAccount_id())) {
                outcome += transaction.getAmount();
            } else {
                income += transaction.getAmount();
            }
        }
        income_lbl.setText(income + " " + account.getCurrency());
        expense_lbl.setText(outcome + " " + account.getCurrency());
        hello_lbl.setText("Welcome back, " + username + "!");
        balance.setText(Double.toString(balanceReg));
        selectedAccount_lbl.setText("Account: " + UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccountNumber() + " transactions");
        currency_lbl.setText(account.getCurrency());
    }

    public DashboardController() {
        login_date.setText(LocalDate.now().toString());

        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        var account_list = userLoggedIn.getCheckingAccounts();
        Map.Entry<String, BankAccount> entry = UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next();
        var account = entry.getValue();

        if (UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount() != null) {
            account = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();
            acc_selected_number_lbl.setText(account.getAccountNumber());
        }
        acc_selected_number_lbl.setText(account.getAccountNumber());
        var balanceReg = account.getBalance();
        var transactionsList = account.getTransactions();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();
        try {
            transactionsList = transactionsList.stream().limit(5).toList();
            observableTransactionList.addAll(transactionsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ObservableList<BankAccount> observableAccountsList = FXCollections.observableArrayList();
        observableAccountsList.addAll(account_list.values().stream().toList());
        account_listview.itemsProperty().set(observableAccountsList);
        setAccountlistView(account_listview);

        transaction_listview.itemsProperty().set(observableTransactionList);
        transaction_listview.setCellFactory(param -> new TransactionListCell());
        double income = 0;
        double outcome = 0;
//        for (Transaction transaction : transactionsList) {
//            if (transaction.getSender() == userLoggedIn.getUserId()) { //todo -> metoda sa iau tranzactii pe baza numarului de cont
//                outcome += transaction.getAmount();
//            } else {
//                income += transaction.getAmount();
//            }
//        }
        income_lbl.setText(income + " " + account.getCurrency());
        expense_lbl.setText(outcome + " " + account.getCurrency());
        hello_lbl.setText("Welcome back, " + username + "!");
        balance.setText(Double.toString(balanceReg));
        currency_lbl.setText(account.getCurrency());
    }
    /*
     * TODO
     *  - la selectie pentru account, se vede ce cont este selectat + tranzactiile se afiseaza pe baza contului
     *    selectat
     * */
    private void setAccountlistView(ListView<BankAccount> listView) {
        listView.setCellFactory(l -> new AccountListCell() {

            // create all nodes that could be displayed
            {
                // update HBox every time the selection changes
                selectedProperty().addListener((observable, oldValue, newValue) -> {
                    BankAccount item = getItem();
                    if (!isEmpty() && item != null) {
                        updateItemSelection(item, newValue);
                    }
                });
            }

            @Override
            protected void updateItem(BankAccount item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                }
            }

            private void updateItemSelection(BankAccount item, boolean selected) {
                // update for HBox for non-empty cells based on selection
                if (selected) {
                    UserLoggedIn.getInstance().getLoggedInUser().setSelectedCheckingAccount(item);
                    selectedAccount_lbl.setText("Account: " + item.getAccountNumber() + " transactions");
                    updatePage();
                }
            }
        });
    }


    private void updatePage() {
        var account = UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount();
        var transactionsList = account.getTransactions();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();
        try {
            transactionsList = transactionsList.stream().limit(5).toList();
            observableTransactionList.addAll(transactionsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ObservableList<BankAccount> observableAccountsList = FXCollections.observableArrayList();
        observableAccountsList.addAll(UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().values().stream().toList());
        account_listview.itemsProperty().set(observableAccountsList);
        setAccountlistView(account_listview);

        transaction_listview.itemsProperty().set(observableTransactionList);
        transaction_listview.setCellFactory(param -> new TransactionListCell());
        double income = 0;
        double outcome = 0;
        for (Transaction transaction : transactionsList) {
            if (Objects.equals(transaction.getSender(), account.getAccount_id())) {
                outcome += transaction.getAmount();
            } else {
                income += transaction.getAmount();
            }
        }
        income_lbl.setText(income + " " + account.getCurrency());
        expense_lbl.setText(outcome + " " + account.getCurrency());
        hello_lbl.setText("Welcome back, " + UserLoggedIn.getInstance().getLoggedInUser().getUsername() + "!");
        balance.setText(Double.toString(account.getBalance()));
        currency_lbl.setText(account.getCurrency());
    }
}
