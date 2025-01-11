package com.example.mazebank.Controllers.Admin.Search;
import com.example.mazebank.Controllers.Admin.Search.Cell.SearchResultCell;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.Users.DB_Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
public ListView<User> searchResult_listview = new ListView<>();
public TextField username_fld = new TextField();
public Button search_btn = new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(this::onSearch);
    }

    public SearchController() {
        search_btn.setOnAction(this::onSearch);
    }

    private void onSearch(Event actionEvent) {
        if(!Objects.equals(username_fld.getText(), "")) {
            if(username_fld.getText().length() < 3){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter more than 3 characters!");
                alert.showAndWait();
                return;
            }
            ObservableList<User> searchResult_observable = FXCollections.observableArrayList(DB_Users.SearchUsers(username_fld.getText()));
            searchResult_listview.setItems(searchResult_observable);
            searchResult_listview.setCellFactory(param -> new SearchResultCell());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Field Username cannot be empty");
            alert.showAndWait();
        }
    }
}
