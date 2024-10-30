package com.example.mazebank.Controllers.User.BankAccounts.Cell;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;

public class AccountListCell extends ListCell<BankAccount> {

    @Override
    protected void updateItem(BankAccount account, boolean empty) {
        super.updateItem(account, empty);
        if (empty || account == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/BankAccounts/Cell/AccountCell.fxml"));
                AnchorPane hbox = loader.load();
                AccountListCellController controller = loader.getController();
                controller.setAccount_balance(account.getBalance());
                controller.setAccount_currency(account.getCurrency());
                controller.setAccount_number(account.getAccount_id());
                controller.setAccount_CVV(account.getCVV());
                SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
                String formattedDate = formatter.format(account.getExpire_date());
                controller.setAccount_expire_date(formattedDate);
                setGraphic(hbox);
            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
            }
        }
    }
}
