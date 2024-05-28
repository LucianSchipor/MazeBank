package com.example.mazebank.Controllers.User;

import com.example.mazebank.Models.DBUtils.DBUtil_Users;
import com.example.mazebank.Models.User;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
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
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        var account = userLoggedIn.getCheckingAccount();
        var balanceReg = account.balanceProperty().get();
        hello_lbl.setText("Welcome back, " + username + "!");
        balance.setText(Double.toString(balanceReg));
        currency_lbl.setText(account.getCurrency());
        refreshpg_btn.setOnAction(this::onRefreshPg);
        initialized = true;
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
