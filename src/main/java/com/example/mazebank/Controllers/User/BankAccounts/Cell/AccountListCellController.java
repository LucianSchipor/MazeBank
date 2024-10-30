package com.example.mazebank.Controllers.User.BankAccounts.Cell;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountListCellController implements Initializable {
    public Label account_name = new Label();

    public BankAccount account;
    public Label balance;
    public Label checking_acc_num;
    public Label currency_lbl;
    public Label expire_date_lbl;
    public Label cvv_lbl;

    public AccountListCellController() {
    }


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setAccount_balance(double balance) {
        this.balance.setText(String.format(Double.toString(balance)));
    }

    public void setAccount_currency(String account_currency) {
        this.currency_lbl.setText(account_currency);
    }

    public void setAccount_number(String account_number) {
        StringBuilder formattedString = new StringBuilder();

        for (int i = 0; i < account_number.length(); i++) {
            formattedString.append(account_number.charAt(i));

            if ((i + 1) % 4 == 0 && i != account_number.length() - 1) {
                formattedString.append(" ");
            }
        }
        this.checking_acc_num.setText(formattedString.toString());
    }

    public void setAccount_CVV(String CVV) {
        this.cvv_lbl.setText(CVV);
    }

    public void setAccount_expire_date(String expire_date) {
        this.expire_date_lbl.setText(expire_date);
    }

}
