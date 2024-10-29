package com.example.mazebank.Controllers.Admin.Accounts;

import com.example.mazebank.Core.Users.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAdress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;

    private final Client client;
    public Label id;

    public ClientCellController(Client client){
        this.client = client;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
