package com.example.mazebank.Core.Alert;

import com.example.mazebank.Core.Models.Model;
public class CustomAlert{

    private final AlertType alert;
    public CustomAlert(AlertType a){
        this.alert = a;
    }

    public void setContentText() {
    }

    public void showAndWait(){
        switch (this.alert){
            case ERROR -> Model.getInstance().getViewFactory().showErrorAlertWindow();
            case INFO -> Model.getInstance().getViewFactory().showInfoAlertWindow();
            default -> Model.getInstance().getViewFactory().showConfirmationAlertWindow();
        }
    }
}
