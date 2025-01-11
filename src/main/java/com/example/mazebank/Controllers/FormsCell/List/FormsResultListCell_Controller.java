package com.example.mazebank.Controllers.FormsCell.List;

import com.example.mazebank.Controllers.FormsCell.FormsResultCell_Controller;
import com.example.mazebank.Core.Forms.Form;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class FormsResultListCell_Controller extends ListCell<Form> {

    @Override
    protected void updateItem(Form form, boolean empty) {
        super.updateItem(form, empty);
        if (empty || form == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Forms/FormsResult/FormsResultCell.fxml"));
                AnchorPane hbox = loader.load();
                FormsResultCell_Controller controller = loader.getController();
                controller.setform(form);
                setGraphic(hbox);


            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getCause());
            }
        }
    }
}
