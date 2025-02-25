package com.example.mazebank.Controllers.Admin.Search.BankAccounts;

import com.example.mazebank.Controllers.Admin.Search.BankAccounts.Cell.SearchResultListCell_BankAccounts;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultController_BankAccounts implements Initializable {

    public ListView<BankAccount> bAcc_listView = new ListView<>();
    public Label bacc_title_lbl = new Label();
    private User user;

    public SearchResultController_BankAccounts(User user) {
        try {
            this.user = user;
            bacc_title_lbl.setText(user.getUsername() + "'s Bank Accounts");
            ObservableList< BankAccount > bankAccountObservableList = FXCollections.observableArrayList(DB_BankAccounts.GetBankAccounts(user.getUserId()).values().stream().toList());
            bAcc_listView.setItems(bankAccountObservableList);
            bAcc_listView.setCellFactory(param -> new SearchResultListCell_BankAccounts());
        }
        catch (Exception e){
            System.out.println("[LOG][Admin_SearchResultController] - " + e.getMessage());
            System.out.println("[LOG][Admin_SearchResultController] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public SearchResultController_BankAccounts() {
        try {
            ObservableList< BankAccount > bankAccountObservableList = FXCollections.observableArrayList(DB_BankAccounts.GetBankAccounts(user.getUserId()).values().stream().toList());
            bAcc_listView.setItems(bankAccountObservableList);
            bAcc_listView.setCellFactory(param -> new SearchResultListCell_BankAccounts());
        }
        catch (Exception e){
            System.out.println("[LOG][Admin_BankAccounts_SearchResultController] - " + e.getMessage());
            System.out.println("[LOG][Admin_BankAccounts_SearchResultController] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bacc_title_lbl.setText(user.getUsername() + "'s Bank Accounts");
        ObservableList< BankAccount > bankAccountObservableList = FXCollections.observableArrayList(DB_BankAccounts.GetBankAccounts(user.getUserId()).values().stream().toList());
        bAcc_listView.setItems(bankAccountObservableList);
        bAcc_listView.setCellFactory(param -> new SearchResultListCell_BankAccounts());
    }
}
