package com.example.mazebank.Controllers;
import com.example.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    login_btn.setOnAction(event ->
    onLogin());
    }

    private void onLogin(){
        //Gets the current stage based on label's parent
        Stage stage = (Stage)error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        Model.getInstance().getViewFactory().showClientWindow();
    }
}
