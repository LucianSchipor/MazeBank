package com.example.mazebank.Controllers.Admin.Forms.Cell;

import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormStatus;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Repositories.DBUtils.DB_Forms;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormsResultCell_Controller implements Initializable {
    public Label id;
    public AnchorPane anchorPane;
    public Button viewForm_btn;
    public Button reject_btn;
    public Button accept_btn;
    public Label status_lbl;
    public Form form;
    public Label username_lbl;
    public HBox username_HBox;

    public FormsResultCell_Controller(Form form) {
        this.form = form;
    }

    public FormsResultCell_Controller() {

    }

    public void selectCell() {
        anchorPane.setStyle("-fx-background-color:  #AAAAAA" + ";");
    }

    private void setStatus() {
        if (form.getStatus().equals(FormStatus.PENDING)) {
            this.status_lbl.setText("Pending");
            this.status_lbl.setTextFill(Paint.valueOf("#5787db"));
        } else {
            if (form.getStatus().equals(FormStatus.ACCEPTED)) {
                this.status_lbl.setText("Accepted");
                this.status_lbl.setTextFill(Paint.valueOf("#228B22"));
            } else {
                this.status_lbl.setText("Rejected");
                this.status_lbl.setTextFill(Paint.valueOf("#FF0000"));
            }
        }
    }

    public void setform(Form form) {
        this.form = form;
        this.id.setText(Integer.toString(form.getForm_id()));
        if(UserLoggedIn.getInstance().getLoggedInUser().getRole().equals(AccountType.ADMIN)) {
            username_HBox.setVisible(true);
            username_lbl.setText(DB_Users.SearchUserById(form.getUser_id()).getUsername());
            accept_btn.setVisible(true);
            reject_btn.setVisible(true);
        }
        else{
            username_HBox.setVisible(false);
        }
        setStatus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accept_btn.setOnAction(this::onAcceptForm);
        accept_btn.setVisible(false);
        reject_btn.setOnAction(this::onRejectForm);
        reject_btn.setVisible(false);
        viewForm_btn.setOnAction(this::onViewForm);
    }

    private void onRejectForm(ActionEvent actionEvent) {
        this.form.setStatus(FormStatus.REJECTED);
        DB_Forms.UpdateFormStatus(this.form.getForm_id(), FormStatus.REJECTED);
        setStatus();
        System.out.println("[LOG][FormCell] - Form rejected");
    }

    private void onAcceptForm(ActionEvent actionEvent) {
        this.form.setStatus(FormStatus.ACCEPTED);
        this.status_lbl.setText("Accepted");
        DB_Forms.UpdateFormStatus(this.form.getForm_id(), FormStatus.ACCEPTED);
        setStatus();
        System.out.println("[LOG][FormCell] - Form accepted");

    }


    private void onViewForm(ActionEvent actionEvent) {
        String filePath = this.form.getForm_path();

        try {
            File file = new File(filePath);

            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } else {
                System.out.println("[LOG][Forms] - file not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
