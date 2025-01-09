package com.example.mazebank.Controllers.User.Transactions.Cell;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.time.format.DateTimeFormatter;
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                controller.trans_date_lbl.setText(transaction.getTimestamp().toLocalDateTime().format(formatter));
                setGraphic(hbox);
                if (Objects.equals(transaction.getSender(), UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id())) {

                    controller.setFromAccountId("to " + transaction.getTo_username());
                    controller.setFromBankAccountNumber(transaction.getReciever_BankAccount().getIBAN());
                    controller.setAmount(transaction.getAmount(), "red");
                } else {
                    controller.setAmount(transaction.getAmount(), "green");
                    controller.setFromAccountId("from " + transaction.getFrom_username());
                    controller.setFromBankAccountNumber(transaction.getSender_BankAccount().getIBAN());
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
