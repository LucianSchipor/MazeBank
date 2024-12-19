package com.example.mazebank.Controllers.User.TempUser;

import com.example.mazebank.Controllers.Admin.Forms.Cell.FormsResultListCell_Controller;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import com.example.mazebank.Repositories.DBUtils.DB_Forms;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TempUserDashboardController implements Initializable {

    public Label login_date;
    public Text hello_lbl;
    List<Form> forms = new ArrayList<>();
    public ListView forms_listview = new ListView();
    ObservableList<Form> forms_observable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_date.setText(LocalDate.now().toString());
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        var username = userLoggedIn.getUsername();
        hello_lbl.setText("Welcome back, " + username + "!");
        forms = DB_Forms.GetFormsById(UserLoggedIn.getInstance().getLoggedInUser().getUserId());
        forms_observable = FXCollections.observableArrayList(forms);
        forms_listview.setItems(forms_observable);
        forms_listview.setCellFactory(param -> new FormsResultListCell_Controller());
        Platform.runLater(() -> {
            forms_listview.setItems(forms_observable);
        });
    }

    public TempUserDashboardController() {
    }

    public void updatePage() {
        forms = DB_Forms.GetFormsById(UserLoggedIn.getInstance().getLoggedInUser().getUserId());
        forms_observable = FXCollections.observableArrayList(forms);
        forms_listview.setItems(forms_observable);
        System.out.println("[LOG][TempUserDashboard] - Page Updated");
    }
}
