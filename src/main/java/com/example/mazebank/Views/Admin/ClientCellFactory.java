package com.example.mazebank.Views.Admin;

import com.example.mazebank.Controllers.Admin.Accounts.ClientCellController;
import com.example.mazebank.Core.Users.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<Client> {

    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
    if(empty){
        setText(null);
        setGraphic(null);
    }
    else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Accounts/ClientCell.fxml"));
        ClientCellController controller = new ClientCellController(client);
        loader.setController(controller);
        setText(null);
        try{
            setGraphic(loader.load());
        }
        catch (Exception e){
            System.out.println("[LOG] - " + e.getMessage());
        }
    }
    }
}
