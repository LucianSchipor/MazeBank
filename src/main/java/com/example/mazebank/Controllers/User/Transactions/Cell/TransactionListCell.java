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
                controller.setAmount(transaction.getAmount());
                controller.reciever_lbl.setText(transaction.getMessage());
                controller.currency_lbl.setText(transaction.getCurrency());
                setGraphic(hbox);
                if (Objects.equals(transaction.getSender(), UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id())) {
                    controller.out_icon.setGlyphName("LONG_ARROW_LEFT");
                    controller.setFromAccountId(transaction.getTo_username());
                    Paint paint;
                    paint = Paint.valueOf("#e40000");
                    controller.out_icon.setFill(paint);
                } else {
                    controller.setFromAccountId(transaction.getFrom_username());
                    controller.out_icon.setGlyphName("LONG_ARROW_RIGHT");
                    Paint paint;
                    paint = Paint.valueOf("#00b10f");
                    controller.out_icon.setFill(paint);                }
            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
            }
        }
    }
}
