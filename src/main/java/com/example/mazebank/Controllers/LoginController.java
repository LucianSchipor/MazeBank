package com.example.mazebank.Controllers;

import com.example.mazebank.Models.DBUtils.DBUtil_Users;
import com.example.mazebank.Models.Model;
import com.example.mazebank.Models.AccountType;
import javafx.collections.FXCollections;
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
    public ChoiceBox<AccountType> account_selector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event ->
                onLogin(event));
    }

    private void onLogin(Event event) {
        //Gets the current stage based on label's parent
        String username = username_fld.getText();
        String password = password_fld.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                var userLoggedIn = DBUtil_Users.loginUser(event, username, password);
                if (userLoggedIn != null && (userLoggedIn.getRole() == AccountType.CLIENT || userLoggedIn.getRole() == AccountType.ADMIN)) {
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
