package com.example.mazebank.Controllers.User.TempUser;

import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormStatus;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.DBUtils.DB_Forms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TempUserDashboardController implements Initializable {

    public Label login_date;
    public Text hello_lbl;
    public Label e_mail_input;
    public Label lname_input;
    public Label fname_input;
    public Label status_input;
    public Button upgrade_btn;
    public Label username_input;
    Form form;
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
        form = forms.getFirst();
        var currentUser = UserLoggedIn.getInstance().getLoggedInUser();
        e_mail_input.setText(currentUser.getEmail());
        username_input.setText(currentUser.getUsername());
        lname_input.setText("soon");
        fname_input.setText("soon");
        if(!form.getStatus().equals(FormStatus.ACCEPTED)){
            upgrade_btn.setVisible(false);
        }
        setStatus();
        updatePage();
    }

    private void setStatus() {
        if (form.getStatus().equals(FormStatus.PENDING)) {
            this.status_input.setText("Pending");
            this.status_input.setTextFill(Paint.valueOf("#5787db"));
        } else {
            if (form.getStatus().equals(FormStatus.ACCEPTED)) {
                this.status_input.setText("Accepted");
                this.status_input.setTextFill(Paint.valueOf("#228B22"));
            } else {
                this.status_input.setText("Rejected");
                this.status_input.setTextFill(Paint.valueOf("#FF0000"));
            }
        }
    }
    public TempUserDashboardController() {
        updatePage();
    }

    public void updatePage() {
        forms = DB_Forms.GetFormsById(UserLoggedIn.getInstance().getLoggedInUser().getUserId());
        forms_observable = FXCollections.observableArrayList(forms);
        forms_listview.setItems(forms_observable);
        System.out.println("[LOG][TempUserDashboard] - Page Updated");
    }
}
