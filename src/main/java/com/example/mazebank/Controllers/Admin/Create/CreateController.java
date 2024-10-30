package com.example.mazebank.Controllers.Admin.Create;

import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;
import com.example.mazebank.Repositories.Users.DB_Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateController implements Initializable {
    public TextField users_username_fld;
    public Button users_create_btn;
    public TextField users_fName_fld;
    public TextField users_lName_fld;
    public TextField users_Email_fld;
    public TextField users_phoneNumber_fld;
    public TextField bAcc_username_fld;
    public Button bAcc_create_btn;
    public ChoiceBox choicebox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users_create_btn.setOnAction(this::onCreate);
        bAcc_create_btn.setOnAction(this::onCreateBankAccount);
        ObservableList<String> Currencies = FXCollections.observableArrayList();
        Currencies.add("RON");
        Currencies.add("EUR");
        Currencies.add("CHF");
        Currencies.add("GBP");
        choicebox.setItems(Currencies);
    }

    private void onCreateBankAccount(Event event) {
        if(bAcc_username_fld.getText().isEmpty() || (choicebox.getValue() == null) || choicebox.getValue().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One field is empty!");
            alert.showAndWait();
            return;
        }
        try {
            int user_id = Integer.parseInt(bAcc_username_fld.getText());
            DB_BankAccounts.CreateBankAccount(user_id, choicebox.getValue().toString());
        }
        catch (Exception exception) {
            System.out.println("[LOG] - " + "one field is empty");
        }
        finally {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Successfully created bank account for user!");
            alert.showAndWait();
        }
    }

    private void onCreate(Event event) {
        String username = users_username_fld.getText();
        if (username.isEmpty() || Objects.equals(username, "")) {
            if (Objects.equals(username, "")) {
                System.out.println("[LOG] - " + "one field is empty");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("One field is empty");
                alert.showAndWait();
            }
        }
        else{
            DB_Users.SignupUser(username, username);
        }
    }
}
