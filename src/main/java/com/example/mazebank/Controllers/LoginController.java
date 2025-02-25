package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Security.SecurityManager;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.AccountType;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField password_fld;
    public Label error_lbl;
    public TextField username_fld;
    public Button createAccount_btn;
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(this::onLogin);
        username_fld.setOnKeyPressed(this::handleEnterKey);
        password_fld.setOnKeyPressed(this::handleEnterKey);
        createAccount_btn.setOnAction(this::onCreateAccount);
    }

    private void onCreateAccount(Event event) {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showRegisterFormWindow();
    }

    private void showClientWindow() {
        try {
            var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
            var checkingAccount = DB_BankAccounts.GetBankAccounts(userLoggedIn.getUserId());
            try {
                //Gets first element from hashmap
                Map.Entry<String, BankAccount> entry = UserLoggedIn.getInstance().getLoggedInUser().getCheckingAccounts().entrySet().iterator().next();
                var value = entry.getValue();
                UserLoggedIn.getInstance().getLoggedInUser().setCheckingAccounts(checkingAccount);
                UserLoggedIn.getInstance().getLoggedInUser().setSelectedCheckingAccount(
                        value);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User " + userLoggedIn.getUsername() + " doesn't have any account from this bank.");
                alert.showAndWait();
                return;
            }
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showClientWindow();
        } catch (Exception e) {
            System.out.println("[LOG][LoginController] - " + e.getMessage());
            System.out.println("[LOG][LoginController] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void onLogin(Event event) {
        try {
            //Gets the current stage based on label's parent
            String username = username_fld.getText();
            String password = password_fld.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                var userLoggedIn = DB_Users.loginUser(username, password);
                try {
                    if (userLoggedIn != null) {
                        UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
                        if (userLoggedIn.getRole() == AccountType.CLIENT) {
                            if (!SecurityManager.getInstance().isFA_Enabled() || !SecurityManager.getInstance().isFA_Verified()) {
                                FA_Check();
                            } else {
                                showClientWindow();
                            }
                        } else {
                            if (userLoggedIn.getRole() == AccountType.ADMIN) {
                                Stage stage = (Stage) error_lbl.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(stage);
                                Model.getInstance().getViewFactory().showAdminWindow();
                            } else {
                                Stage stage = (Stage) error_lbl.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(stage);
                                Model.getInstance().getViewFactory().showTempUserDashboard();
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("User does not exist!");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    System.out.println("[LOG][LoginController] - " + e.getCause());
                    System.out.println("[LOG][LoginController] - " + e.getLocalizedMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("One field is empty");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("[LOG][LoginController] - " + e.getMessage());
            System.out.println("[LOG][LoginController] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    private void FA_Check() {
        if (!SecurityManager.getInstance().isFA_Enabled()) {
            SecurityManager.getInstance().setAuthCodes();
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().show2FAWindow(error_lbl.getScene());

        } else {
            SecurityManager.getInstance().setAuthCodes(SecurityManager.getInstance().getFA_Key());
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().show2FAWindow(error_lbl.getScene());
        }
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLogin(event);
        }
    }

}
