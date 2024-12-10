package com.example.mazebank.Core.Alert;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Users.AccountType;
import javafx.scene.control.Alert;

public class CustomAlert{

    AlertType alert;
    private String message;
    public CustomAlert(AlertType a){
        this.alert = a;
    }

    public void setContentText(String message) {
        this.message = message;
    }

    public void showAndWait(){
        AlertType type = this.alert;
        switch (type){
            case ERROR -> Model.getInstance().getViewFactory().showErrorAlertWindow();
            case INFO -> Model.getInstance().getViewFactory().showInfoAlertWindow();
            default -> Model.getInstance().getViewFactory().showConfirmationAlertWindow();
        }
    }
}
