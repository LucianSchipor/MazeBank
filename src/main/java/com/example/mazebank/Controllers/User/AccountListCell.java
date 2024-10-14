package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.CheckingAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class AccountListCell extends ListCell<CheckingAccount> {

    @Override
    protected void updateItem(CheckingAccount account, boolean empty) {
        super.updateItem(account, empty);
        if (empty || account == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/AccountCell.fxml"));
                AnchorPane hbox = loader.load();
                AccountListCellController controller = loader.getController();
                controller.setAccount_balance(account.getBalance());
                controller.setAccount_currency(account.getCurrency());
                controller.setAccount_number(account.getAccount_id());
//                controller.setId_lbl(account.getAccount_id());
                setGraphic(hbox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
