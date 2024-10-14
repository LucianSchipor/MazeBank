package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.CheckingAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountListCellController implements Initializable {
    public Label account_name= new Label();

    public CheckingAccount account;
    public Label balance;
    public Label checking_acc_num;
    public Label currency_lbl;
    public Label id_lbl;

    public AccountListCellController() {}


    public void initialize(URL location, ResourceBundle resources) {
    }

public void setAccount_balance(double balance){
this.balance.setText(String.format(Double.toString(balance)));
}

public void setId_lbl(String id_lbl){
//        this.id_lbl.setText("Id: " + id_lbl);
}
public void setAccount_number(String account_number){
        this.checking_acc_num.setText(account_number);
}

public void setAccount_currency(String account_currency){
        this.currency_lbl.setText(account_currency);
}

}
