package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Alert.AlertType;
import com.example.mazebank.Core.Alert.CustomAlert;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.AccountType;
import com.google.zxing.WriterException;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
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

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLogin(event);
        }
    }


    private void onCreateAccount(Event event) {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showRegisterFormWindow();
    }

    private void onSignUp(Event event) {
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (Objects.equals(username, "") || Objects.equals(password, "")) {
            System.out.println("[LOG] - " + "one field is empty");
            CustomAlert alert = new CustomAlert(AlertType.ERROR);
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


    private void ShowClientWindow(){
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
    }

    private void onLogin(Event event) {
        //Gets the current stage based on label's parent
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            var userLoggedIn = DB_Users.LoginUser(username, password);
            try {
                if (userLoggedIn != null) {
                    UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
                    if (userLoggedIn.getRole() == AccountType.CLIENT) {
                        if (!Security.getInstance().isFA_Enabled() || !Security.getInstance().isFA_Verified()) {
                            FA_Check();
                        }
                        else{
                            ShowClientWindow();
                        }
                    } else {
                        if(userLoggedIn.getRole() == AccountType.ADMIN){
                            Stage stage = (Stage) error_lbl.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().showAdminWindow();
                        }
                        else{
                            Stage stage = (Stage) error_lbl.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().showTempUserDashboard();
                        }

                    }
                }
                else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("User does not exist!");
                        alert.showAndWait();
                    }
            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getMessage());
            }
        } else {
            CustomAlert alert = new CustomAlert(AlertType.ERROR);
            alert.setContentText("One field is empty");
            alert.showAndWait();
        }
    }

    private void FA_Check() throws IOException, WriterException {
        if (!Security.getInstance().isFA_Enabled()) {
            Security.getInstance().setAuthCodes();
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().show2FAWindow(error_lbl.getScene());

        } else {
            Security.getInstance().setAuthCodes(Security.getInstance().getFA_Key());
            Stage stage = (Stage) error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().show2FAWindow(error_lbl.getScene());
        }
    }
}
