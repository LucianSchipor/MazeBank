package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.BankAccounts.CheckingAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.AccountType;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public TextField username_fld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(this::onLogin);
    }

    private void onLogin(Event event) {
        //Gets the current stage based on label's parent
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                var userLoggedIn = DB_Users.LoginUser(username, password);
                if (userLoggedIn != null && (userLoggedIn.getRole() == AccountType.CLIENT || userLoggedIn.getRole() == AccountType.ADMIN)) {
                    UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
                    var checkingAccount = DB_BankAccounts.GetBankAccounts(userLoggedIn.getUserId());
                    try {
                        //Gets first element from hashmap
                        Map.Entry<String, CheckingAccount> entry = UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next();
                        var value=entry.getValue();
                        UserLoggedIn.getInstance().getLoggedInUser().setCheckingAccounts(checkingAccount);
                        UserLoggedIn.getInstance().getLoggedInUser().setSelectedCheckingAccount(
                                value);
                    }
                    catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("User " + userLoggedIn.getUsername() + " doesn't have any account from this bank.");
                        alert.showAndWait();
                        return;
                    }
                    Stage stage = (Stage) error_lbl.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    if (userLoggedIn.getRole() == AccountType.CLIENT) {
                        Model.getInstance().getViewFactory().showClientWindow();
                    } else {
                        Model.getInstance().getViewFactory().showAdminWindow();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("User does not exist!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username or Password is empty!");
            alert.showAndWait();
        }
    }
}
