package com.example.mazebank.Repositories.Forms;

import com.example.mazebank.Core.Forms.*;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import com.example.mazebank.Repositories.Users.DB_Users;
import javafx.scene.control.Alert;
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

    private static String CreateFormDocument(Form form) {
        String formPath;
        List<Pair<String, String>> Text = new ArrayList<>();
        var formCreator = DB_Users.SearchUserById(form.getUser_id());

        assert formCreator != null;
        CreateText(Text, form, formCreator);
        Text.add(new Pair<>("Form Type: ", form.getFormType().toString()));
        try (PDDocument document = new PDDocument()) {
            CreatePDF(Text, document);
            String path;
            if (form.getFormType().equals(FormType.ACCOUNT)) {
                path = "Forms/Account/" + "form" + form.getForm_id() + "_user" + form.getUser_id() + "_ft" + form.getFormType() + "_d" + form.getDate() + ".pdf";
                File file = new File(path);
                document.save(file);
                System.out.println("[LOG][Forms] - PDF created successfully! Path: " + file.getAbsolutePath());
            } else {
                path = "Forms/Credits/" + "form" + form.getForm_id() + "_user" + form.getUser_id() + "_ft" + form.getFormType() + "_d" + form.getDate() + ".pdf";

                File file = new File(path);
                document.save(file);
                System.out.println("[LOG][Forms] - PDF created successfully! Path: " + file.getAbsolutePath());
            }
            formPath = path;

        } catch (IOException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        return formPath;
    }

    private static void CreatePDF(List<Pair<String, String>> text, PDDocument document) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        String logoPath = "src/main/resources/Images/Icon/bank.png";

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            PDImageXObject logo = PDImageXObject.createFromFile(logoPath, document);
            float logoWidth = 60;
            float logoHeight = 60;
            float logoX = 20;
            float logoY = 690;
            contentStream.drawImage(logo, logoX, logoY, logoWidth, logoHeight);
            float titleX = logoX + logoWidth + 10;
            float titleY = logoY + logoHeight - 40;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 20);
            contentStream.newLineAtOffset(titleX, titleY);
            contentStream.showText("Maze Bank");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 16);
            contentStream.newLineAtOffset(260, 500);
            text.forEach(n -> {
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
    }

    private static void CreateText(List<Pair<String, String>> text, Form form, User formCreator) {
        try {
            if (form instanceof FormCredit) {
                FormCredit creditForm = (FormCredit) form;
                creditForm.GetDetails().forEach(n ->
                        text.add(new Pair<>(n.getKey(), n.getValue())));
            } else if (form instanceof FormAccount) {
                FormAccount accountForm = (FormAccount) form;
            }
        } catch (Exception e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
        }
    }

    private static void CreateText(List<Pair<String, String>> text, User formCreator) {
        text.add(new Pair<>("Username: ", formCreator.getUsername()));
        text.add(new Pair<>("E-Mail: ", formCreator.getEmail()));
        text.add(new Pair<>("Last Name: ", "soon"));
        text.add(new Pair<>("First Name: ", "soon"));
    }

    private static void CreateDocument() {
        List<Pair<String, String>> Text = new ArrayList<>();
        var userLoggedIn = UserLoggedIn.getInstance().getLoggedInUser();
        CreateText(Text, userLoggedIn);
        try (PDDocument document = new PDDocument()) {
            CreatePDF(Text, document);
            File file = new File("Forms/" + Text.getFirst().getValue() + "_Register_Form.pdf");
            document.save(file);
            System.out.println("[LOG][Forms] - PDF created successfully! Path: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    public static void UpdateFormStatus(int form_id, FormStatus status) {
        PreparedStatement querry;
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("UPDATE forms SET status = ? WHERE form_id = ?");
            querry.setInt(2, form_id);
            querry.setInt(1, FormStatus.valueOf(status.name()).ordinal());
            int rowsAffected = querry.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[LOG][DB_Forms] - successfully deleted form!");
                try {
                    CreateDocument();
                } catch (Exception e) {
                    System.out.println("[LOG][DB_Forms] - " + e.getCause());
                    System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteForm(int form_id) {
        PreparedStatement querry;
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
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
                } catch (Exception e) {
                    System.out.println("[LOG][Forms] - " + e.getCause());
                    System.out.println("[LOG][Forms] - " + e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateForm(FormType type) {
        PreparedStatement querry;
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("INSERT INTO forms(form_path, date, status, user_id, form_type) values (?, ?, ?, ?, ?)");
            querry.setString(1, "Unknown");
            querry.setDate(2, Date.valueOf(LocalDate.now()));
            querry.setInt(3, 0);
            querry.setInt(4, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            querry.setInt(5, FormType.valueOf(type.name()).ordinal());

            querry.executeUpdate();
            System.out.println("[LOG][Forms] - successfully created form!");
            try {
                querry = connection.prepareStatement("UPDATE forms SET form_path = ? where form_id = ?");
                var createdForm = DB_Forms.GetFormsById(
                                UserLoggedIn.getInstance().getLoggedInUser().getUserId())
                        .getLast();
                querry.setString(1, CreateFormDocument(createdForm));
                querry.setInt(2, createdForm.getForm_id());
                querry.executeUpdate();
            } catch (Exception e) {
                System.out.println("[LOG][Forms] - " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateForm(Form form) {
        PreparedStatement querry;
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        try {
            assert connection != null;
            querry = connection.prepareStatement("INSERT INTO forms(form_path, date, status, user_id, form_type) values (?, ?, ?, ?, ?)");
            form.SetFormPath("Unknown");
            querry.setString(1, form.getForm_path());
            querry.setDate(2, Date.valueOf(LocalDate.now()));
            querry.setInt(3, form.getStatus().ordinal());
            querry.setInt(4, UserLoggedIn.getInstance().getLoggedInUser().getUserId());
            querry.setInt(5, form.getFormType().ordinal());
            querry.executeUpdate();

            querry = connection.prepareStatement("SELECT MAX(form_id) AS max_form_id FROM forms");
            var resultSet = querry.executeQuery();
            while (resultSet.next()) {
                form.SetFormId(resultSet.getInt("max_form_id"));
            }
            form.SetFormPath(CreateFormDocument(form));
            querry = connection.prepareStatement("UPDATE forms SET form_path = ? where form_id = ?");
            querry.setString(1, form.getForm_path());
            querry.setInt(2, form.getForm_id());
            querry.executeUpdate();
            System.out.println("[LOG][Forms] - successfully created form!");
            try {
            } catch (Exception e) {
                System.out.println("[LOG][Forms] - " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Form> GetFormsById(int userId) {
        PreparedStatement querry;
        List<Form> forms = new ArrayList<>();
        ResultSet resultSet;
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
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
                if (status == 0) formStatus = FormStatus.PENDING;
                if (status == 1) formStatus = FormStatus.ACCEPTED;
                if (status == 2) formStatus = FormStatus.REJECTED;
                Form form = new Form(form_id, DB_Users.SearchUserById(user_id), path, resultSet.getDate("date"), formStatus, form_type);
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
        Connection connection;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (SQLException e) {
            System.out.println("[LOG][DB_Forms] - " + e.getCause());
            System.out.println("[LOG][DB_Forms] - " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter more than 3 characters!");
            alert.showAndWait();
            throw new RuntimeException(e);
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
                if (status == 0) formStatus = FormStatus.PENDING;
                if (status == 1) formStatus = FormStatus.ACCEPTED;
                if (status == 2) formStatus = FormStatus.REJECTED;
                Form form = new Form(form_id, DB_Users.SearchUserById(user_id), path, resultSet.getDate("date"), formStatus, form_type);
                forms.add(form);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return forms;
    }
}
