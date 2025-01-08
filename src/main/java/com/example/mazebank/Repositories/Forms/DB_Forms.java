package com.example.mazebank.Repositories.Forms;

import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormStatus;
import com.example.mazebank.Core.Forms.FormType;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB_Forms {

    private static void CreateDocument(){
        List<Pair<String, String>> Text = new ArrayList<>();
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        Text.add(new Pair<>("Username: ", userLoggedIn.getUsername()));
        Text.add(new Pair<>("E-Mail: ", userLoggedIn.getEmail()));
        Text.add(new Pair<>("Last Name: ", "soon"));
        Text.add(new Pair<>("First Name: ", "soon"));
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
                        contentStream.showText(n.getKey());
                        contentStream.showText(n.getValue());
                        contentStream.newLineAtOffset(0, -25);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                contentStream.endText();
            }
            File file = new File("Forms/" + Text.getFirst().getValue() + "_Register_Form.pdf");
            document.save(file);
            System.out.println("[LOG][Forms] - PDF created successfully! Path: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateFormStatus(int form_id, FormStatus status){
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("UPDATE forms SET status = ? WHERE form_id = ?");
            querry.setInt(2, form_id);
            querry.setInt(1, FormStatus.valueOf(status.name()).ordinal());
            int rowsAffected = querry.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[LOG][Forms] - successfully deleted form!");
                try {
                    CreateDocument();
                }
                catch (Exception e){
                    System.out.println("[LOG][Forms] - " + e.getCause());
                    System.out.println("[LOG][Forms] - " + e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteForm(int form_id){
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("DELETE from FORMS where form_id = ?");
            querry.setInt(1, form_id);
            int rowsAffected = querry.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[LOG][Forms] - successfully deleted form!");
                try {
                    CreateDocument();
                }
                catch (Exception e){
                    System.out.println("[LOG][Forms] - " + e.getCause());
                    System.out.println("[LOG][Forms] - " + e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateForm(FormType type){
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("INSERT INTO forms(form_path, date, status, user_id, form_type) values (?, ?, ?, ?, ?)");
            querry.setString(1, "Forms/" + UserLoggedIn.getInstance().getLoggedInUser().getUsername() + "_Register_Form.pdf");
            querry.setDate(2, Date.valueOf(LocalDate.now()));
            querry.setInt(3, 0);
            querry.setInt(4, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            querry.setInt(5, FormType.valueOf(type.name()).ordinal());

            int rowsAffected = querry.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[LOG][Forms] - successfully created form!");
                try {
                    CreateDocument();
                }
                catch (Exception e){
                    System.out.println("[LOG][Forms] - " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void CreateForm(Form form){
        PreparedStatement querry;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("INSERT INTO forms(form_path, date, status, user_id, form_type) values (?, ?, ?, ?, ?)");
            querry.setString(1, "Forms/" + UserLoggedIn.getInstance().getLoggedInUser().getUsername() + "_Register_Form.pdf");
            querry.setDate(2, Date.valueOf(LocalDate.now()));
            querry.setInt(3, 0);
            querry.setInt(4, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            querry.setInt(5, FormType.valueOf(form.getFormType().toString()).ordinal());

            int rowsAffected = querry.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[LOG][Forms] - successfully created form!");
                try {
                    CreateDocument();
                }
                catch (Exception e){
                    System.out.println("[LOG][Forms] - " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Form> GetFormsById(int userId){
        PreparedStatement querry;
        List<Form> forms = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("SELECT * FROM forms where user_id = ?");
            querry.setInt(1, userId);
            resultSet = querry.executeQuery();
            while (resultSet.next()) {
                int form_id = resultSet.getInt("form_id");
                String path = resultSet.getString("form_path");
                int user_id = resultSet.getInt("user_id");
                int status = resultSet.getInt("status");
                int form_type = resultSet.getInt("form_type");
                FormStatus formStatus = null;
                if(status == 0) formStatus = FormStatus.PENDING;
                if(status == 1) formStatus = FormStatus.ACCEPTED;
                if(status == 2) formStatus = FormStatus.REJECTED;
                Form form = new Form(form_id, user_id, path, resultSet.getDate("date"), formStatus, form_type);
                forms.add(form);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return forms;
    }
    public static List<Form> GetForms() {
        PreparedStatement querry;
        List<Form> forms = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("SELECT * FROM forms");
            resultSet = querry.executeQuery();
            while (resultSet.next()) {
                int form_id = resultSet.getInt("form_id");
                String path = resultSet.getString("form_path");
                int user_id = resultSet.getInt("user_id");
                int status = resultSet.getInt("status");
                int form_type = resultSet.getInt("form_type");
                FormStatus formStatus = null;
                if(status == 0) formStatus = FormStatus.PENDING;
                if(status == 1) formStatus = FormStatus.ACCEPTED;
                if(status == 2) formStatus = FormStatus.REJECTED;
                Form form = new Form(form_id, user_id, path, resultSet.getDate("date"), formStatus, form_type);
                forms.add(form);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return forms;
    }
}
