package com.example.mazebank.Controllers.User.Transactions.Cell;

import com.example.mazebank.Core.Transactions.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {


    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label reciever_lbl;
    public Label amount_lbl;
    public Label currency_lbl;

    private Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    public TransactionCellController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setFromAccountId(String fromAccountId) {
        sender_lbl.setText(fromAccountId);
    }

    public void setAmount(double amount) {
        amount_lbl.setText(String.valueOf(amount));
    }
}
