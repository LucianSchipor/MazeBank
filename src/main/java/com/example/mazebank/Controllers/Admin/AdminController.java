package com.example.mazebank.Controllers.Admin;

import com.example.mazebank.Core.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {

            try {
                switch (newVal){
                    case SEARCH -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSearchView());
                    case FORMS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getFormsView());
                    default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateView());
                }
            }
            catch (Exception e){
                System.out.println("[LOG][AdminController] - " + e.getMessage());
            }
        });
    }
}
