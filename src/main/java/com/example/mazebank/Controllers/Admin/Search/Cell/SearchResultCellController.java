package com.example.mazebank.Controllers.Admin.Search.Cell;

import com.example.mazebank.Core.Users.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultCellController implements Initializable {

    public Label username_lbl;
    public Label id;
    private User user;

    public SearchResultCellController(){

    }

    public SearchResultCellController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUserDetails(User user) {
        this.id.setText(String.valueOf(user.getUserId()));
        this.username_lbl.setText(user.getUsername());
    }

}
