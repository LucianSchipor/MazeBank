package com.example.mazebank.Controllers.Admin.Forms.Cell;

import com.example.mazebank.Controllers.Admin.Forms.FormsController;
import com.example.mazebank.Controllers.User.Transactions.Cell.TransactionCellController;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Transactions.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormsResultCell_Controller implements Initializable {
    public Label id;
    public AnchorPane anchorPane;
    public Button viewForm_btn;
    public Button reject_btn;
    public Button accept_btn;
    public Label status_lbl;
    public Form form;

    public FormsResultCell_Controller(Form form){
        this.form = form;
    }

    public FormsResultCell_Controller(){

    }

    public void selectCell(){
        anchorPane.setStyle("-fx-background-color:  #AAAAAA" + ";");
    }


    public void setform(Form form){
        this.form = form;
        this.id.setText( Integer.toString(form.getForm_id()));
        if(form.getStatus() == 0){
            this.status_lbl.setText("Pending");
            this.status_lbl.setTextFill(Paint.valueOf("#5787db"));
        }
        else{
            if(form.getStatus() == 1){
                this.status_lbl.setText("Accepted");
                this.status_lbl.setTextFill(Paint.valueOf("#228B22"));
            }
            else{
                this.status_lbl.setText("Rejected");
                this.status_lbl.setTextFill(Paint.valueOf("#FF0000"));

            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewForm_btn.setOnAction(this::onViewForm);
    }

    private void onViewForm(ActionEvent actionEvent){
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
