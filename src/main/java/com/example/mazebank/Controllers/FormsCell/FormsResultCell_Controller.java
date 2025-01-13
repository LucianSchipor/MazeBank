package com.example.mazebank.Controllers.FormsCell;

import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormStatus;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Repositories.Forms.DB_Forms;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    public Label formtype_lbl;

    public FormsResultCell_Controller() {

    }

    private void setStatus() {
        this.username_lbl.setTextFill(Paint.valueOf("#000000"));
        this.id.setTextFill(Paint.valueOf("#000000"));
        this.formtype_lbl.setTextFill(Paint.valueOf("#000000"));
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
        anchorPane.setStyle("-fx-background-color: transparent");
        this.id.setText(Integer.toString(form.getForm_id()));
        if (UserLoggedIn.getInstance().getLoggedInUser().getRole().equals(AccountType.ADMIN)) {
            username_HBox.setVisible(true);
            username_lbl.setText("User id: " + form.getUser_id());
            accept_btn.setVisible(true);
            reject_btn.setVisible(true);
            formtype_lbl.setText(form.getFormType().toString());
            formtype_lbl.setVisible(true);
        } else {
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
        formtype_lbl.setVisible(false);
    }

    private void onRejectForm(ActionEvent actionEvent) {
        try {
            this.form.setStatus(FormStatus.REJECTED);
            DB_Forms.updateFormStatus(this.form.getForm_id(), FormStatus.REJECTED);
            setStatus();
            System.out.println("[LOG][FormCell] - Form rejected");
        } catch (Exception e) {
            System.out.println("[LOG][FormCell] - " + e.getMessage());
            System.out.println("[LOG][FormCell] - " + e.getLocalizedMessage());

        } finally {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Success");
            alert.setContentText("Form: " + form.getForm_id() + " rejected!");
            alert.showAndWait();
        }

    }

    private void onAcceptForm(ActionEvent actionEvent) {
        try {
            this.form.setStatus(FormStatus.ACCEPTED);
            this.status_lbl.setText("Accepted");
            DB_Forms.updateFormStatus(this.form.getForm_id(), FormStatus.ACCEPTED);
            setStatus();
            System.out.println("[LOG][FormCell] - Form accepted");
        } catch (Exception e) {
            System.out.println("[LOG][FormCell] - " + e.getMessage());
            System.out.println("[LOG][FormCell] - " + e.getLocalizedMessage());

        } finally {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Success");
            alert.setContentText("Form: " + form.getForm_id() + " accepted!");
            alert.showAndWait();
        }
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
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }


}
