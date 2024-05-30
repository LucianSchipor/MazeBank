
module com.example.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.management;

    opens com.example.mazebank to javafx.fxml;
    exports com.example.mazebank;
    exports  com.example.mazebank.Controllers;
    exports com.example.mazebank.Controllers.Admin;
    exports com.example.mazebank.Controllers.User;
    exports com.example.mazebank.Core.Models;
    exports com.example.mazebank.Views;
    exports com.example.mazebank.Repositories.DBUtils;
}