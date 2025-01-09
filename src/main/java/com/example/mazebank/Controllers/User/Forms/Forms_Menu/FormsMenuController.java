package com.example.mazebank.Controllers.User.Forms.Forms_Menu;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.User.Forms.FormsMenuOptions;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FormsMenuController implements Initializable {


    public Button account_btn;
    public Button credits_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        account_btn.setOnAction(event -> onAccount());
        credits_btn.setOnAction(event -> onCredits());
    }

    private void onCredits() {
        System.out.println("[LOG] - redirecting to Credits panel");
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().set(FormsMenuOptions.CREDITS);
    }

    private void onAccount() {
        System.out.println("[LOG] - redirecting to Credits panel");
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().set(FormsMenuOptions.ACCOUNT);
    }
}
