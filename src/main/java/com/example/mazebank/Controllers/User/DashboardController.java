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
    public Label checking_bal = new Label();
    public Label checking_acc_num = new Label();
    public Label savings_bal = new Label();
    public Label savings_acc_num = new Label();
    public Label income_lbl = new Label();
    public Label expense_lbl = new Label();
    public TextField payee_fld = new TextField();
    public TextField amount_fld = new TextField();
    public TextArea message_fld = new TextArea();
    public Button send_money_btn = new Button("Send Money");
    public ListView transaction_listview = new ListView();
    public Label login_date = new Label();
    public Label balance = new Label();
    public Text hello_lbl = new Text("Hello");
    public Button refreshpg_btn = new Button("Refresh Page");
    public Label currency_lbl = new Label();
    boolean initialized = true;


    /*
    TODO
        -> la logout + login iar, dashboard nu se reincarca cu datele utilizatorului (nu mi intra iar in initialize)

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send_money_btn.setOnAction(this::onSendMoney);
        login_date.setText(LocalDate.now().toString());
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        var account = userLoggedIn.getCheckingAccount();
        var balanceReg = account.balanceProperty().get();
        var transactionsList = DBUtil_Users.getUserTransactions(userLoggedIn.getUserId()).stream().limit(5).toList();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList();
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
        income_lbl.setText(income + " " + account.getCurrency());
        expense_lbl.setText(outcome + " " + account.getCurrency());
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
            assert cacc != null;
            balance.setText(Double.toString(cacc.balanceProperty().get()));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
