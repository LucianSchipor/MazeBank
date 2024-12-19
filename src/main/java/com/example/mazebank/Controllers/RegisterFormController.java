package com.example.mazebank.Controllers;

import com.example.mazebank.Repositories.DBUtils.DB_Forms;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    public Button createForm_btn;
    public Label username_lbl;
    public TextField lName_fld;
    public TextField fName_fld;
    public TextField email_fld;
    public TextField username_fld;
    private List<Pair<String, String>> Text = new ArrayList<Pair<String, String>>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createForm_btn.setOnAction(this::onCreateForm);
    }

    private void onCreateForm(javafx.event.ActionEvent actionEvent) {
        Text.add(new Pair<>("Username: ", username_fld.getText()));
        Text.add(new Pair<>("E-Mail: ", email_fld.getText()));
        Text.add(new Pair<>("Last Name: ", lName_fld.getText()));
        Text.add(new Pair<>("First Name: ", fName_fld.getText()));
        DB_Forms.CreateForm();
    }

    private void createDocument(List<Pair<String, String>> Text){
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            String logoPath = "src/main/resources/Images/Icon/bank.png";

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDImageXObject logo = PDImageXObject.createFromFile(logoPath, document);
                float logoWidth = 100;
                float logoHeight = 100;
                float logoX = 20;
                float logoY = 690;
                contentStream.drawImage(logo, logoX, logoY, logoWidth, logoHeight);
                float titleX = logoX + logoWidth + 10;
                float titleY = logoY + logoHeight - 60;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 28);
                contentStream.newLineAtOffset(titleX, titleY);
                contentStream.showText("Maze Bank");
                contentStream.endText();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(260, 500);
                Text.forEach(n -> {
                    try {
                        contentStream.showText(n.getKey().toString());
                        contentStream.showText(n.getValue().toString());
                        contentStream.newLineAtOffset(0, -25);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                contentStream.endText();
            }
            File file = new File("Forms/" + Text.get(0).getValue() + "_Register_Form.pdf");
            document.save(file);
            System.out.println("[LOG][RegisterForm] - PDF created successfully! Path: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
