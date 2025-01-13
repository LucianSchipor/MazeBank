package com.example.mazebank.Controllers.User.TempUser;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Views.User.Menu.TempUserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TempUserMenuController implements Initializable {

    public Button dashboard_btn;
    public Button createForm_btn;
    public Button logout_btn;


    public TempUserMenuController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    addListeners();
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        createForm_btn.setOnAction(event -> onCreateForm());
        logout_btn.setOnAction(event -> onLogOut());

    }

    private void onCreateForm() {
        System.out.println("[LOG] - redirecting to Temp Create panel");
        Model.getInstance().getViewFactory().getTempUserSelectedMenuItem().set(TempUserMenuOptions.CREATE);
    }

    private void onDashboard(){
        System.out.println("[LOG] - redirecting to Temp Dashboard panel");
        Model.getInstance().getViewFactory().getTempUserSelectedMenuItem().set(TempUserMenuOptions.DASHBOARD);
    }

    private void onLogOut(){
        UserLoggedIn.getInstance().setLoggedInUser(null);
        Stage stage = (Stage)dashboard_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
