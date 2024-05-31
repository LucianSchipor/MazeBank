package com.example.mazebank.Controllers.User;

import com.example.mazebank.Repositories.DBUtils.DBUtil_Users;
import com.example.mazebank.Core.Models.Transaction;
import com.example.mazebank.Core.Models.User;
import com.example.mazebank.Repositories.DB_Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class DashboardController implements Initializable {
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public ListView transaction_listview;
    public Label login_date;
    public User user;
    public Label balance;
    public Text hello_lbl;
    public Button refreshpg_btn;
    public Label currency_lbl;
    boolean initialized;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send_money_btn.setOnAction(this::onSendMoney);
        login_date.setText(LocalDate.now().toString());
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        var account = userLoggedIn.getCheckingAccount();
        var balanceReg = account.balanceProperty().get();
        var transactionsList = DBUtil_Users.getUserTransactions(userLoggedIn.getUserId()).stream().limit(5).toList();
        assert transactionsList != null;
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();;
        observableTransactionList.addAll(transactionsList);
        transaction_listview.itemsProperty().set(observableTransactionList);
        transaction_listview.setCellFactory(param -> new TransactionListCell());
        double income = 0;
        double outcome = 0;
        for (Transaction transaction : transactionsList) {
            if (transaction.getFrom_account_id() == userLoggedIn.getUserId()) {
                outcome += transaction.getAmount();
            }
            else{
                income += transaction.getAmount();
            }
        }
        income_lbl.setText(Double.toString(income) + " " + account.getCurrency());
        expense_lbl.setText(Double.toString(outcome) + " " + account.getCurrency());
        hello_lbl.setText("Welcome back, " + username + "!");
        balance.setText(Double.toString(balanceReg));
        currency_lbl.setText(account.getCurrency());
        refreshpg_btn.setOnAction(this::onRefreshPg);
        initialized = true;
    }

   private void onSendMoney(Event event){

       DB_Transactions.transferMoneyToAccount(payee_fld.getText(), amount_fld.getText(), message_fld.getText());
    }

    private void onRefreshPg(Event event){
        var cacc = DBUtil_Users.getUserAccount(event, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
        try{
            balance.setText(Double.toString(cacc.balanceProperty().get()));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
