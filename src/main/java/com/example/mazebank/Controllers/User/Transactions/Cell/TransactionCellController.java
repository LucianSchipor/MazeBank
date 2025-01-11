package com.example.mazebank.Controllers.User.Transactions.Cell;

import com.example.mazebank.Core.Transactions.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {


    public Label trans_date_lbl;
    public Label amount_lbl;
    public Label currency_lbl;
    public AnchorPane anchorPane;
    public Label fromBAccNumber_lbl;
    public Button viewTransactionDetails_btn;
    public Button deleteTransaction_btn;
    public Label sender_lbl;
    public Label message_lbl;

    private Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    public TransactionCellController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuItem copyMenuItem = new MenuItem("Copy");
        copyMenuItem.setOnAction(event -> copyLabelToClipboard());

        ContextMenu contextMenu = new ContextMenu(copyMenuItem);

        fromBAccNumber_lbl.setOnContextMenuRequested(event ->
                contextMenu.show(fromBAccNumber_lbl, event.getScreenX(), event.getScreenY()));
    }
    public void copyLabelToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(fromBAccNumber_lbl.getText());
        clipboard.setContent(content);
    }

    public void setFromAccountId(String fromAccountId) {
        sender_lbl.setText(fromAccountId);
        sender_lbl.setStyle("-fx-text-fill: #000000");
    }

    public void setMessage(String message) {
        message_lbl.setText(message);
        message_lbl.setStyle("-fx-text-fill: #000000");

    }

    public void setFromBankAccountNumber(String fromBankAccountNumber) {
        fromBAccNumber_lbl.setText(fromBankAccountNumber);
        fromBAccNumber_lbl.setStyle("-fx-text-fill: #000000");
    }

    public void setAmount(double amount, String color) {
        if(color.equals("green")){
            amount_lbl.setText("+" + amount);
            amount_lbl.setStyle("-fx-text-fill: #228B22");
        }
        else{
            amount_lbl.setText("-" + amount);
            amount_lbl.setStyle("-fx-text-fill: #FF0000");

        }
    }
}
