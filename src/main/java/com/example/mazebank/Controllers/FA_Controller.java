package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.google.zxing.WriterException;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.mazebank.Core.Security.Security.createQRCode;

public class FA_Controller implements Initializable {

    public ImageView imageView;
    public Button verify_btn;
    public Label code_lbl;
    public TextField otp_fld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            verify_btn.setOnAction(event -> {
                try {
                    enable_2FA(event); // Apelează metoda și gestionează excepțiile
                } catch (IOException | WriterException e) {
                    e.printStackTrace();
                    // Poți adăuga și o notificare pentru utilizator
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return createQRCode(Security.getInstance().getAuthCodes().getValue());
    }

    private void enable_2FA(Event event) throws IOException, WriterException {
        if (Security.getInstance().verifyOTP(otp_fld.getText())) {
            DB_Users.Enable2FA(UserLoggedIn.getInstance().getLoggedInUser(), Security.getInstance().getAuthCodes().getKey());
            Stage stage = (Stage) imageView.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showClientWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("OTP Code is invalid!");
            alert.showAndWait();
            return;
        }
    }

    private void verify_2FA(Event event) throws IOException, WriterException {

    }
}
