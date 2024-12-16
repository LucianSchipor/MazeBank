package com.example.mazebank.Controllers.Admin.Search.Cell;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Views.ViewFactory;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultCellController implements Initializable {

    public Label username_lbl;
    public Label id;
    public Button delete_btn;
    public Button bankAcc_btn;
    public AnchorPane anchor_pane;
    private User user;

    public SearchResultCellController(){

    }

    public SearchResultCellController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bankAcc_btn.setOnAction(this::onBankAccountPressed);
    }

    public void onBankAccountPressed(Event event) {
        Model.getInstance().getViewFactory().showAdminOptionsForUserWindow(user);
    }

    public void setUserDetails(User user) {
        this.user = user;
        this.id.setText(String.valueOf(user.getUserId()));
        this.username_lbl.setText(user.getUsername());
    }

    public void selectCell(){
        anchor_pane.setStyle("-fx-background-color:  #AAAAAA" + ";");
    }
}
