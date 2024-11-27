package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.google.zxing.WriterException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.mazebank.Core.Security.Security.createQRCode;

public class FA_Controller implements Initializable {

    public ImageView imageView;
    public Button verify_btn;
    public Label code_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    verify_btn.setOnAction(this::verify);
        try {
            Pair<String, String> keyPair = Security.getInstance().getAuthCodes();
            imageView.setImage(getQRCode());
            code_lbl.setText(keyPair.getKey());
            Security.getInstance().startSecurityThread(keyPair.getKey());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private Image getQRCode() throws IOException, WriterException {  //Used ONCE
        Security.getInstance();
        DB_Users.Enable2FA(UserLoggedIn.getInstance().getLoggedInUser(), Security.getInstance().getAuthCodes().getKey());
        return createQRCode(Security.getInstance().getAuthCodes().getValue());
    }
    private void verify(Event event){

    }

}
