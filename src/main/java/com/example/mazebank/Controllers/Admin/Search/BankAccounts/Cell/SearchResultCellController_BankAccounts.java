package com.example.mazebank.Controllers.Admin.Search.BankAccounts.Cell;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SearchResultCellController_BankAccounts implements Initializable {
    public Label cardNumber_lbl;
    public Label date_lbl;
    public Label balance_lbl;
    public Label currency_lbl;
    private BankAccount bankAccount;

    public SearchResultCellController_BankAccounts(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public SearchResultCellController_BankAccounts() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCellDetails(BankAccount bankAccount) {
        String formattedCardNumber = bankAccount.getAccount_id().replaceAll("(.{4})", "$1 ");
        this.cardNumber_lbl.setText(formattedCardNumber.trim());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
        String formattedDate = formatter.format(bankAccount.getExpire_date());
        this.currency_lbl.setText(bankAccount.getCurrency());
        this.date_lbl.setText(formattedDate);
        this.balance_lbl.setText(bankAccount.getBalance() + "");
    }
}
