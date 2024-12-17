package com.example.mazebank.Controllers.User.Transactions.Cell;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.Objects;

public class TransactionListCell extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions/Cell/TransactionCell.fxml"));
                AnchorPane hbox = loader.load();
                TransactionCellController controller = loader.getController();
                controller.currency_lbl.setText(transaction.getCurrency());
                setGraphic(hbox);
                if (Objects.equals(transaction.getSender(), UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id())) {
                    controller.setAmount(transaction.getAmount(), "green");
                    controller.setFromAccountId("from " + transaction.getTo_username());
                    controller.setFromBankAccountNumber(transaction.getReciever_BankAccount().getIBAN());
                } else {
                    controller.setFromAccountId("to " + transaction.getFrom_username());
                    controller.setFromBankAccountNumber(transaction.getSender_BankAccount().getIBAN());
                    controller.setAmount(transaction.getAmount(), "red");
                }



//                if(isSelected()) {
//                    controller.selectCell();
//                }

            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
            }

        }
    }
}
