package com.example.mazebank.Controllers.Admin.Search.Cell;

import com.example.mazebank.Core.Users.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SearchResultCell extends ListCell<User> {
    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Search/SearchResult/SearchResultCell.fxml"));
                try {
                    AnchorPane hbox = loader.load();
                    SearchResultCellController controller = loader.getController();
                    controller.setUserDetails(user);
                    setGraphic(hbox);
                } catch (IOException e) {
                    System.out.println("[LOG] - Error on creating Cells for Search Result - " + e.getMessage());
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
