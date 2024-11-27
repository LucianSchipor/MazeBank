package com.example.mazebank.Controllers;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Security;
import com.google.zxing.WriterException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.mazebank.Core.Security.Security.createQRCode;

public class FA_Controller implements Initializable {

    public ImageView imageView;
    public Button verify_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    verify_btn.setOnAction(this::verify);
        try {
            imageView.setImage(getQRCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }



//    public FA_Controller(){
//        verify_btn.setOnAction(this::verify);
//        try {
//            imageView = new ImageView(getQRCode());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (WriterException e) {
//            throw new RuntimeException(e);
//        }
//    }


    private Image getQRCode() throws IOException, WriterException {
        var qrBarCode = Security.getInstance().generateQR_enable_2FA(UserLoggedIn.getInstance().getLoggedInUser());
        return createQRCode(qrBarCode);

    }
    private void verify(Event event){

    }

}
