package com.example.mazebank.Controllers.Admin.Menu;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Views.Admin.Menu.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button forms_btn;
    public Button logout_btn;
    public Button create_btn;
    public Button search_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        setStyleSelectedButton(create_btn);
    }

    private void addListeners(){
    create_btn.setOnAction(event -> onCreate());
    search_btn.setOnAction(event -> onSearch());
    forms_btn.setOnAction(event -> onForms());
    logout_btn.setOnAction(event -> onLogOut());
    }

    public void onForms(){
        System.out.println("[LOG] - redirecting to Forms panel");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.FORMS);
        deselectAll();
        setStyleSelectedButton(forms_btn);
    }

    private void onSearch(){
        System.out.println("[LOG] - redirecting to Search panel");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.SEARCH);
        deselectAll();
        setStyleSelectedButton(search_btn);
    }

    private void onCreate(){
        System.out.println("[LOG] - redirecting to Create panel");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE);
        deselectAll();
        setStyleSelectedButton(create_btn);
    }

    private void onLogOut(){
        Stage stage = (Stage)logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE);
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    private void setStyleSelectedButton(Button button){
        String selectButtonString = """
                -fx-pref-width: 130;
                -fx-background-color: #AAAAAA; \
                -fx-text-fill: #FFFFFF;   \
                 -fx-pref-height: 40;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";
        button.setStyle(selectButtonString);
    }

    private void deselectAll(){
        String deselectButtonString = """
                -fx-pref-width: 130;
                    -fx-pref-height: 40;
                    -fx-background-color: #FFFFFF;
                    -fx-fill: #132A13;
                    -fx-font-size: 1.1em;
                    -fx-alignment: center_left;
                    -fx-effect: dropshadow(three-pass-box, #DDDDDD, 5, 0, 0, 6);""";

        create_btn.setStyle(deselectButtonString);
        search_btn.setStyle(deselectButtonString);
        forms_btn.setStyle(deselectButtonString);
    }
}
