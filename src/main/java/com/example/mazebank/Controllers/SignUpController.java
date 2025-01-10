package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Forms.FormType;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.Forms.DB_Forms;
import com.example.mazebank.Repositories.Users.DB_Users;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    public Button createForm_btn;
    public Label username_lbl;
    public TextField lName_fld;
    public TextField fName_fld;
    public TextField email_fld;
    public TextField username_fld;
    private List<Pair<String, String>> Text = new ArrayList<Pair<String, String>>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createForm_btn.setOnAction(this::onCreateForm);
    }

    private void onCreateForm(javafx.event.ActionEvent actionEvent) {
        try{
            String username = username_fld.getText();
            String password = username_fld.getText();
            DB_Users.SignupUser(username, password);
            var userLoggedIn = DB_Users.LoginUser(username, password);
            UserLoggedIn.getInstance().setLoggedInUser(userLoggedIn);
            assert userLoggedIn != null;

            DB_Forms.CreateForm(FormType.ACCOUNT);

            Stage stage = (Stage) username_fld.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showUserWindow(userLoggedIn);
        }
        catch (Exception e) {
            System.out.println("[LOG][RegisterFormController] " + e.getMessage());
            System.out.println("[LOG][RegisterFormController] " + e.getLocalizedMessage());
        }

    }

}