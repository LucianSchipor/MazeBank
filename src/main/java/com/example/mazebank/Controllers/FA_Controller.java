package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Repositories.Users.DB_Users;
import com.google.zxing.WriterException;
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
                    Update2FAKey();
                } catch (IOException | WriterException e) {
                    System.out.println("[LOG][DB_Forms] - " + e.getCause());
                    System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter more than 3 characters!");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        try {
            Pair<String, String> keyPair = Security.getInstance().getAuthCodes();
            imageView.setImage(getQRCode());
            code_lbl.setText(keyPair.getKey());
            Security.getInstance().startSecurityThread(keyPair.getKey());
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private Image getQRCode() throws IOException, WriterException {  //Used ONCE
        Security.getInstance();
        return createQRCode(Security.getInstance().getAuthCodes().getValue());
    }

    private void Update2FAKey() throws IOException, WriterException {
        if (Security.getInstance().verifyOTP(otp_fld.getText())) {
            DB_Users.update2FAKey(UserLoggedIn.getInstance().getLoggedInUser(), Security.getInstance().getAuthCodes().getKey());
            Verify2FA();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("OTP Code is invalid!");
            alert.showAndWait();
        }
    }

    private void Verify2FA() {
        try {
            if (Security.getInstance().verifyOTP(otp_fld.getText())) {
                DB_Users.updateFAVerificationTime(UserLoggedIn.getInstance().getLoggedInUser());
                Security.getInstance().setFA_Verified(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Success!");
                alert.setContentText("2FA verified for " + UserLoggedIn.getInstance().getLoggedInUser().getUsername());
                alert.showAndWait();
                Stage stage = (Stage) otp_fld.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showPreviousWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("OTP Code is invalid!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("[LOG][FA_Controller] - " + e.getCause());
            System.out.println("[LOG][FA_Controller] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
