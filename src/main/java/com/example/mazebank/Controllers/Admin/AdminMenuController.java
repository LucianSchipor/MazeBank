package com.example.mazebank.Controllers.Admin;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;
    public Button create_btn;
    public Button modify_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        setStyleSelectedButton(create_btn);
    }

    private void addListeners(){
    create_btn.setOnAction(event -> onCreateClient());
    clients_btn.setOnAction(event -> onClients());
    deposit_btn.setOnAction(event -> onDeposit());
    logout_btn.setOnAction(event -> onLogOut());
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

    private void deselectAll(){
        String deselectButtonString = """
                -fx-pref-width: 130;
                    -fx-pref-height: 40;
                    -fx-background-color: #FFFFFF;
                    -fx-fill: #132A13;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";

        create_btn.setStyle(deselectButtonString);
        clients_btn.setStyle(deselectButtonString);
        deposit_btn.setStyle(deselectButtonString);
    }


    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
        deselectAll();
        setStyleSelectedButton(create_btn);
    }

    private void onClients(){
        clients_btn.setStyle("-fx-background-color: #AAAAAA; -fx-text-fill: #FFFFFF;");
        deselectAll();
        setStyleSelectedButton(clients_btn);
    }

    private void onDeposit(){
        deposit_btn.setStyle("-fx-background-color: #AAAAAA; -fx-text-fill: #FFFFFF;");
        deselectAll();
        setStyleSelectedButton(deposit_btn);
    }

    private void onLogOut(){
        Stage stage = (Stage)logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
