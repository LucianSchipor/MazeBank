package com.example.mazebank.Views.User.Transactions;

import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionCellController;
import com.example.mazebank.Core.Transactions.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TransactionCellFactory extends ListCell<Transaction> {

    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            setText(null);
            try{
                setGraphic(loader.load());
            }
            catch (Exception e){
                System.out.println("[LOG] - " + e.getMessage());
            }
        }
    }
}
