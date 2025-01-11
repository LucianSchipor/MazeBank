package com.example.mazebank.Controllers.User.TempUser;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.User.Menu.TempUserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TempUserController implements Initializable {

    public BorderPane temp_Parent;
    private TempUserMenuController menuController;
    private TempUserDashboardController dashboardController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getTempUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {

            if (Objects.requireNonNull(newVal) == TempUserMenuOptions.CREATE) {
                temp_Parent.setCenter(Model.getInstance().getViewFactory().getRegisterFormWindow());
            } else {
                temp_Parent.setCenter(Model.getInstance().getViewFactory().getTempDashboardView());
            }
        });
    }
}
