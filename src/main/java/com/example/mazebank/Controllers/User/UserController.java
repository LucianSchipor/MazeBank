package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.Model;
import javafx.scene.layout.BorderPane;
public class UserController {

    public BorderPane client_parent;

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
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            switch (newVal){
                case TRANSACTIONS -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        } );
    }
}
