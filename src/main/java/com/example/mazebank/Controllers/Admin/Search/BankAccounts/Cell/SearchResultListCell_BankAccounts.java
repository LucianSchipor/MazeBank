package com.example.mazebank.Controllers.Admin.Search.BankAccounts.Cell;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionCellController;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.Objects;

public class SearchResultListCell_BankAccounts extends ListCell<BankAccount> {
    @Override
    protected void updateItem(BankAccount bankAccount, boolean empty) {
        super.updateItem(bankAccount, empty);
        if (empty || bankAccount == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Search/SearchResult/BankAccounts/Cell/SearchResultCell_BankAccounts.fxml"));
                AnchorPane hbox = loader.load();
                SearchResultCellController_BankAccounts controller = loader.getController();
                controller.setCellDetails(bankAccount);
                setGraphic(hbox);
            } catch (Exception e) {
                System.out.println("[LOG] - Error on trying to create bank account cell " + e.getMessage());
            }
        }
    }
}
