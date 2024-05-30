package com.example.mazebank.Controllers.User;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.UserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transactions_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;
    public Label title_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        transactions_btn.setOnAction(event -> onTransactions());
        logout_btn.setOnAction(event -> onLogOut());
        accounts_btn.setOnAction(event -> onAccounts());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.DASHBOARD);
    }

    private void onTransactions(){
        System.out.println("Transactions button hitted!");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.TRANSACTIONS);
    }
    private void onAccounts(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.ACCOUNTS);
    }

    private void onLogOut(){
        Stage stage = (Stage)dashboard_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
