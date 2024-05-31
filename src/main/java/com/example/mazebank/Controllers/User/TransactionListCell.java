package com.example.mazebank.Controllers.User;
import com.example.mazebank.Core.Models.Transaction;
import javafx.scene.control.ListCell;
public class TransactionListCell extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setText(null);
        } else {
            if(transaction.getFrom_account_id() == UserLoggedIn.getInstance().getLoggedInUser().getUserId()){
            setText("Transaction_Id: " + transaction.getTransaction_id() +
                    ", To: " + transaction.getTo_username() +
                    ", Amount: " + transaction.getAmount() +
                    ", Message: " + transaction.getMessage());
                    setStyle("-fx-background-color: #f57767;");
            }
            else{
                setText("Transaction_Id: " + transaction.getTransaction_id() +
                        ", From: " + transaction.getFrom_username() +
                        ", Amount: " + transaction.getAmount() +
                        ", Message: " + transaction.getMessage());
                setStyle("-fx-background-color: #81f567;");
            }
            }
    }
}
