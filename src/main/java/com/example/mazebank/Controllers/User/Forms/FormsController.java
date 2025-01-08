package com.example.mazebank.Controllers.User.Forms;

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

public class FormsController implements Initializable {


    public ListView account_forms_listview;
    public ListView credits_forms_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var loggedUser = UserLoggedIn.getInstance().getLoggedInUser();
        ObservableList<Form> account_forms_observable = FXCollections.observableArrayList(
                DB_Forms.GetFormsById(loggedUser.getUserId()));
        account_forms_listview.setItems(
                account_forms_observable.filtered(form -> form.getFormType() == FormType.ACCOUNT));
        account_forms_listview.setCellFactory(param -> new FormsResultListCell_Controller());

        ObservableList<Form> credits_forms_observable = FXCollections.observableArrayList(DB_Forms.GetFormsById(loggedUser.getUserId()));
        credits_forms_listview.setItems(
                credits_forms_observable.filtered(form -> form.getFormType() == FormType.FUNDS));
        credits_forms_listview.setCellFactory(param -> new FormsResultListCell_Controller());


    }

    public FormsController(){

    }
}
