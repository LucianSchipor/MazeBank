package com.example.mazebank.Controllers.User;

import com.example.mazebank.Models.User;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    boolean initialized;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        if (username.equals("client")) {
            balance.setText("0.00$");
        } else {
            balance.setText("9.99$");
        }
        initialized = true;
    }

    public DashboardController(){
        if (initialized) {
            if (UserLoggedIn.getInstance().getLoggedInUser().getUsername().equals("client")) {
                balance.setText("0.00$");
            } else {
                balance.setText("9.99$");
            }
        }
    }
}
