package com.example.mazebank.Controllers.User.Forms;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.User.Forms.FormsMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormsController implements Initializable {


    public BorderPane border_pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//


    }

    public FormsController(){
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            if (Objects.requireNonNull(newVal) == FormsMenuOptions.CREDITS) {
                border_pane.setCenter(Model.getInstance().getViewFactory().getFormsCreditsView());
            } else {
                border_pane.setCenter(Model.getInstance().getViewFactory().getFormsAccountView());
            }
        } );
    }
}
