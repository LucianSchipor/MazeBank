package com.example.mazebank.Controllers.Admin;

import com.example.mazebank.Models.Model;
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
    public Button create_account_btn;
    public Button create_Acc_Btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){

    create_Acc_Btn.setOnAction(event -> onCreateClient());
    clients_btn.setOnAction(event -> onClients());
    logout_btn.setOnAction(event -> onLogOut());
    }

    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onClients(){
        System.out.println("Pressed on clients");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }

    private void onLogOut(){
        Stage stage = (Stage)logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
