package com.example.mazebank.Controllers.User.Menu;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public BorderPane client_parent = new BorderPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
    }

    public UserController() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSACTIONS ->
                        client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case ADD_FUDNS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAddFundsView());
                case FORMS -> client_parent.setCenter(Model.getInstance().getViewFactory().getUserFormsView());
                case DASHBOARD -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case LOGGEDOUT -> client_parent.setCenter(Model.getInstance().getViewFactory().getLoginWindow());
                default -> System.out.println("[LOG][UserController] - " + "option set to 0");

            }
        });
    }
}
