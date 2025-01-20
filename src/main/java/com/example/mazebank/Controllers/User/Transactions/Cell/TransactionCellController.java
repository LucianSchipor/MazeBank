package com.example.mazebank.Controllers.User.Transactions.Cell;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import javafx.event.ActionEvent;
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


    private Transaction transaction;
    public Label trans_date_lbl;
    public Label amount_lbl;
    public Label currency_lbl;
    public AnchorPane anchorPane;
    public Label fromBAccNumber_lbl;
    public Button deleteTransaction_btn;
    public Label sender_lbl;
    public Label message_lbl;
    public Button retransfer_btn;
    public boolean hasOptions = true;

    public TransactionCellController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (hasOptions) {
            retransfer_btn.setOnAction(this::onRetransfer);
            MenuItem copyMenuItem = new MenuItem("Copy");
            copyMenuItem.setOnAction(event -> copyLabelToClipboard());

            ContextMenu contextMenu = new ContextMenu(copyMenuItem);

            fromBAccNumber_lbl.setOnContextMenuRequested(event ->
                    contextMenu.show(fromBAccNumber_lbl, event.getScreenX(), event.getScreenY()));
        }
    }

    private void onRetransfer(ActionEvent actionEvent) {
        Model.getInstance().getViewFactory().showTransactionsWithOnePersWindow();

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
        if (color.equals("green")) {
            amount_lbl.setText("+" + amount);
            amount_lbl.setStyle("-fx-text-fill: #228B22");
        } else {
            amount_lbl.setText("-" + amount);
            amount_lbl.setStyle("-fx-text-fill: #FF0000");

        }
    }

    public String getSender() {
        return transaction.getSender();
    }

    public String getReceiver() {
        return transaction.getReceiver();
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        UserLoggedIn.getInstance().setRetransferSelectedTransaction(transaction);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void hasOptions(boolean b) {
        this.hasOptions = b;
    }
}
