package com.example.mazebank.Controllers.User.Forms.Forms_Menu.Account;

import com.example.mazebank.Controllers.FormsCell.List.FormsResultListCell_Controller;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormType;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.Forms.DB_Forms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class FormsMenuAccountController implements Initializable {

    public ListView<Form> listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var loggedUser = UserLoggedIn.getInstance().getLoggedInUser();
        ObservableList<Form> account_forms_observable = FXCollections.observableArrayList(
                DB_Forms.GetFormsById(loggedUser.getUserId()));
        listview.setItems(
                account_forms_observable.filtered(form -> form.getFormType() == FormType.ACCOUNT));
        listview.setCellFactory(param -> new FormsResultListCell_Controller());
    }

    
}
