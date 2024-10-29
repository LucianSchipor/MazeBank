
module com.example.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    opens com.example.mazebank to javafx.fxml;
    exports com.example.mazebank;
    exports  com.example.mazebank.Controllers;
    exports com.example.mazebank.Controllers.Admin;
    exports com.example.mazebank.Views;
    exports com.example.mazebank.Repositories.DBUtils;
    exports com.example.mazebank.Controllers.User.BankAccounts;
    exports com.example.mazebank.Controllers.User.BankAccounts.Cell;
    exports com.example.mazebank.Controllers.User.Transactions;
    exports com.example.mazebank.Controllers.User.Transactions.Cell;
    exports com.example.mazebank.Core.BankAccounts;
    exports com.example.mazebank.Core.Users;
    exports com.example.mazebank.Core.Transactions;
    exports com.example.mazebank.Repositories.BankAccounts;
    exports com.example.mazebank.Repositories.Transactions;
    exports com.example.mazebank.Repositories.Users;
    exports com.example.mazebank.Controllers.User.Menu;
    exports com.example.mazebank.Core.Models;
    exports com.example.mazebank.Controllers.Admin.Menu;
    exports com.example.mazebank.Controllers.Admin.Create;
    exports com.example.mazebank.Controllers.Admin.Accounts;
    exports com.example.mazebank.Controllers.Admin.Deposit;
    exports com.example.mazebank.Controllers.Admin.Search;
    exports com.example.mazebank.Views.Admin;
    exports com.example.mazebank.Views.Admin.Menu;
    exports com.example.mazebank.Views.User.Menu;
    exports com.example.mazebank.Views.User.Transactions;
    exports com.example.mazebank.Controllers.Admin.Search.Cell;
}