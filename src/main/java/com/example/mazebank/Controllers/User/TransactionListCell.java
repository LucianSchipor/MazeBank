package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
public class TransactionListCell extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/TransactionCell.fxml"));
                AnchorPane hbox = loader.load();
                TransactionCellController controller = loader.getController();
                controller.setAmount(transaction.getAmount());
                controller.reciever_lbl.setText(transaction.getMessage());
                controller.currency_lbl.setText(transaction.getCurrency());
                setGraphic(hbox);
                if (transaction.getFrom_account_id() == UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getAccount_id()) {
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
                e.printStackTrace();
            }
        }
    }
}
