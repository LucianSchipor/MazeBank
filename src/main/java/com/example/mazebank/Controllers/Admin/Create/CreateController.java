package com.example.mazebank.Controllers.Admin.Create;

import com.example.mazebank.Repositories.Users.DB_Users;
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
    public TextField bAcc_currency_fld;
    public Button bAcc_create_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users_create_btn.setOnAction(this::onCreate);
        bAcc_create_btn.setOnAction(this::onCreateBankAccount);
    }

    private void onCreateBankAccount(Event event) {
        if(bAcc_username_fld.getText().isEmpty() || bAcc_currency_fld.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One field is empty!");
            alert.showAndWait();
            return;
        }
        try {
            int user_id = Integer.parseInt(bAcc_username_fld.getText());
        }
        catch (Exception exception) {
            System.out.println("[LOG] - " + "one field is empty");
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
