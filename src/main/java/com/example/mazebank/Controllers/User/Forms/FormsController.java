package com.example.mazebank.Controllers.User.Forms;

import com.example.mazebank.Controllers.FormsCell.List.FormsResultListCell_Controller;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormType;
import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.Forms.DB_Forms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FormsController implements Initializable {


    public ListView account_forms_listview;
    public ListView credits_forms_listview;
    public BorderPane border_pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//


    }

    public FormsController(){
        Model.getInstance().getViewFactory().getFormsMenuSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            switch (newVal){
                case CREDITS -> border_pane.setCenter(Model.getInstance().getViewFactory().getFormsCreditsView());
                default -> border_pane.setCenter(Model.getInstance().getViewFactory().getFormsAccountView());
            }
        } );
    }
}
