package com.example.mazebank.Controllers;

import com.example.mazebank.Controllers.User.UserLoggedIn;
import com.example.mazebank.Repositories.DBUtils.DBUtil_Users;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.AccountType;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
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
        //Skiping the verification for debugging
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                var userLoggedIn = DBUtil_Users.loginUser(event, username, password);
                if (userLoggedIn != null && (userLoggedIn.getRole() == AccountType.CLIENT || userLoggedIn.getRole() == AccountType.ADMIN)) {
                    UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
                    var checkingAccount = DBUtil_Users.getUserAccount(event, userLoggedIn.getUserId());
                    UserLoggedIn.getInstance().getLoggedInUser().setCheckingAccount(checkingAccount);
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
