package com.example.mazebank.Controllers.Admin;

import com.example.mazebank.Repositories.Users.DB_Users;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateController implements Initializable {
    public TextField username_fld;
    public Label error_lbl;
    public Button create_Username_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_Username_btn.setOnAction(this::onCreate);
    }

    private void onCreate(Event event) {
        String username = username_fld.getText();
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
