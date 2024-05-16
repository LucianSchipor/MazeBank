package com.example.mazebank.Controllers;
import com.example.mazebank.Models.DBConnection;
import com.example.mazebank.Models.Model;
import com.example.mazebank.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        account_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        account_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        account_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(account_selector.getValue()));
        login_btn.setOnAction(event ->
    onLogin(event));
    }

    private void onLogin(Event event){
        //Gets the current stage based on label's parent
        String username = username_fld.getText();
        String password = password_fld.getText();
        DBConnection.singUnUser(event, username, password);
        Stage stage = (Stage)error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT){
            Model.getInstance().getViewFactory().showClientWindow();
        }
        else{
            Model.getInstance().getViewFactory().showAdminWindow();
        }
    }
}
