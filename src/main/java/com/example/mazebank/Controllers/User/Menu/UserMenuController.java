package com.example.mazebank.Controllers.User.Menu;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.UserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        setStyleSelectedButton(dashboard_btn);
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        transactions_btn.setOnAction(event -> onTransactions());
        logout_btn.setOnAction(event -> onLogOut());
        accounts_btn.setOnAction(event -> onAccounts());
    }

    private void setStyleSelectedButton(Button button){
        String selectButtonString = """
                -fx-pref-width: 130;
                -fx-background-color: #AAAAAA; \
                -fx-text-fill: #FFFFFF;   \
                 -fx-pref-height: 40;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";
            button.setStyle(selectButtonString);
    }
    private void deselectAll(){
        String deselectButtonString = """
                -fx-pref-width: 130;
                    -fx-pref-height: 40;
                    -fx-background-color: #FFFFFF;
                    -fx-fill: #132A13;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";

        dashboard_btn.setStyle(deselectButtonString);
        transactions_btn.setStyle(deselectButtonString);
        accounts_btn.setStyle(deselectButtonString);
    }

    private void onDashboard(){
        System.out.println("[LOG] - redirecting to Dashboard panel");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.DASHBOARD);
        deselectAll();
        setStyleSelectedButton(dashboard_btn);
    }

    private void onTransactions(){
        System.out.println("[LOG] - redirecting to Transactions panel");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.TRANSACTIONS);
        deselectAll();
        setStyleSelectedButton(transactions_btn);
    }

    private void onAccounts(){
        System.out.println("[LOG] - redirecting to Accounts panel");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(UserMenuOptions.ACCOUNTS);
        deselectAll();
        setStyleSelectedButton(accounts_btn);
    }

    private void onLogOut(){
        UserLoggedIn.getInstance().setLoggedInUser(null);
        Stage stage = (Stage)dashboard_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
