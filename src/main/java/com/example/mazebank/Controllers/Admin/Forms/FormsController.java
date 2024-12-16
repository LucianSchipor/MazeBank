package com.example.mazebank.Controllers.Admin.Forms;

import com.example.mazebank.Controllers.Admin.Forms.Cell.FormsResultCell_Controller;
import com.example.mazebank.Controllers.Admin.Forms.Cell.FormsResultListCell_Controller;
import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionListCell;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.DBUtils.DB_Forms;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import com.google.zxing.WriterException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormsController implements Initializable {

    public javafx.scene.control.ListView forms_listView = new ListView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Form> forms_observable = FXCollections.observableArrayList(DB_Forms.GetForms());
        forms_listView.setItems(forms_observable);
        forms_listView.setCellFactory(param -> new FormsResultListCell_Controller());
    }

    public FormsController (){
        ObservableList<Form> forms_observable = FXCollections.observableArrayList(DB_Forms.GetForms());
        forms_listView.setItems(forms_observable);
        forms_listView.setCellFactory(param -> new FormsResultListCell_Controller());
        //TODO -> setat style cand selectez formular
    }
}
