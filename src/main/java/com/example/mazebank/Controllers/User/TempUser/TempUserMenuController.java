package com.example.mazebank.Controllers.User.TempUser;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.User.Menu.TempUserMenuOptions;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TempUserMenuController implements Initializable {

    public Button dashboard_btn;
    public Button createForm_btn;


    public TempUserMenuController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    addListeners();
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        createForm_btn.setOnAction(event -> onCreateForm());
    }

    private void onCreateForm() {
        System.out.println("[LOG] - redirecting to Temp Create panel");
        Model.getInstance().getViewFactory().getTempUserSelectedMenuItem().set(TempUserMenuOptions.CREATE);
    }


    private void onDashboard(){
        System.out.println("[LOG] - redirecting to Temp Dashboard panel");
        Model.getInstance().getViewFactory().getTempUserSelectedMenuItem().set(TempUserMenuOptions.DASHBOARD);
    }

}
