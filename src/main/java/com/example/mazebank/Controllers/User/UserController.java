package com.example.mazebank.Controllers.User;

import com.example.mazebank.Models.Model;
import com.example.mazebank.Models.User;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController {

    public BorderPane client_parent;
    User user;

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
//            switch (newVal) {
//                case TRANSACTIONS ->
//                        client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
//                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
//                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView(user));
//            }
//        });
//    }

    public UserController() {
        this.user = user;
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            switch (newVal){
                case TRANSACTIONS -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        } );
    }
}
