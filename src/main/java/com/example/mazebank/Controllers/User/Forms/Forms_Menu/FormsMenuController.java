package com.example.mazebank.Controllers.User.Forms.Forms_Menu;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.User.Forms.FormsMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FormsMenuController implements Initializable {


    public Button account_btn;
    public Button credits_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStyleSelectedButton(account_btn);
        addListeners();
    }

    private void setStyleSelectedButton(Button button){
        String selectButtonString = """
                -fx-pref-width: 130;
                -fx-background-color: #AAAAAA; \
                -fx-text-fill: #FFFFFF;   \
                 -fx-pref-height: 40;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";
        button.setStyle(selectButtonString);
    }
    private void deselectAll() {
        String deselectButtonString = """
                -fx-pref-width: 130;
                    -fx-pref-height: 40;
                    -fx-background-color: #FFFFFF;
                    -fx-fill: #132A13;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";

        account_btn.setStyle(deselectButtonString);
        credits_btn.setStyle(deselectButtonString);
    }

    private void addListeners(){
        account_btn.setOnAction(event -> onAccount());
        credits_btn.setOnAction(event -> onCredits());
    }

    private void onCredits() {
        System.out.println("[LOG] - redirecting to Credits panel");
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().set(FormsMenuOptions.CREDITS);
        deselectAll();
        setStyleSelectedButton(credits_btn);
    }

    private void onAccount() {
        System.out.println("[LOG] - redirecting to Credits panel");
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().set(FormsMenuOptions.ACCOUNT);
        deselectAll();
        setStyleSelectedButton(account_btn);
    }
}
