package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.Transaction;
import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class TransactionListCell extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
//        if (empty || transaction == null) {
//            setText(null);
//        } else {
//            if(transaction.getFrom_account_id() == UserLoggedIn.getInstance().getLoggedInUser().getUserId()){
//            setText("Transaction_Id: " + transaction.getTransaction_id() +
//                    ", To: " + transaction.getTo_username() +
//                    ", Amount: " + transaction.getAmount() +
//                    ", Message: " + transaction.getMessage());
//                    setStyle("-fx-background-color: #f57767;");
//            }
//            else{
//                setText("Transaction_Id: " + transaction.getTransaction_id() +
//                        ", From: " + transaction.getFrom_username() +
//                        ", Amount: " + transaction.getAmount() +
//                        ", Message: " + transaction.getMessage());
//                setStyle("-fx-background-color: #81f567;");
//            }
//            }
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
                controller.currency_lbl.setText(UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccount().getCurrency());
                setGraphic(hbox);
                if (transaction.getFrom_account_id() == UserLoggedIn.getInstance().getLoggedInUser().getUserId()) {
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
                return;
            }
        }
    }
}
