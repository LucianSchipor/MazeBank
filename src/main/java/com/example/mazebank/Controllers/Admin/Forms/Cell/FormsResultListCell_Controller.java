package com.example.mazebank.Controllers.Admin.Forms.Cell;

import com.example.mazebank.Controllers.Admin.Forms.FormsController;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Models.UserLoggedIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.Objects;

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
//                if(isSelected()){
//                    controller.selectCell();
//                }
            } catch (Exception e) {
                System.out.println("[LOG] - " + e.getCause());
            }
        }
    }
}
