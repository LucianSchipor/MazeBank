package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.AccountType;
import com.google.zxing.qrcode.decoder.Mode;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public TextField username_fld;
    public Button signup_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(this::onLogin);
        signup_btn.setOnAction(this::onSignUp);
        username_fld.setOnKeyPressed(this::handleEnterKey);
        password_fld.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLogin(event);
        }
    }

    private void onSignUp(Event event) {
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (Objects.equals(username, "") || Objects.equals(password, "")) {
            System.out.println("[LOG] - " + "one field is empty");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One field is empty");
            alert.showAndWait();
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                DB_Users.SignupUser(username, password);
            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error on creating a new account.");
                alert.showAndWait();
            }
        }
    }

    private void onLogin(Event event) {
        //Gets the current stage based on label's parent
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            var userLoggedIn = DB_Users.LoginUser(username, password);
            try {
                if (userLoggedIn != null && (userLoggedIn.getRole() == AccountType.CLIENT || userLoggedIn.getRole() == AccountType.ADMIN)) {
                    if (userLoggedIn.getRole() == AccountType.CLIENT) {
                        UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
                        if (!UserLoggedIn.getInstance().getLoggedInUser().isFA_Verified()) {
                            Stage stage = (Stage) error_lbl.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().show2FAWindow();
                        } else {
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
                        }
                    } else {
                        Stage stage = (Stage) error_lbl.getScene().getWindow();
                        Model.getInstance().getViewFactory().closeStage(stage);
                        Model.getInstance().getViewFactory().showAdminWindow();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("User does not exist!");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username or Password is empty!");
            alert.showAndWait();
        }
    }
}
