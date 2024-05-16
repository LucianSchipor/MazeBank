package com.example.mazebank.Views;

import com.example.mazebank.Controllers.Admin.AdminController;
import com.example.mazebank.Controllers.User.DashboardController;
import com.example.mazebank.Controllers.User.UserController;
import com.example.mazebank.Models.AccountType;
import com.example.mazebank.Models.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    //Client Views
    private AnchorPane dashboardView;
    private AccountType loginAccountType;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private final ObjectProperty<UserMenuOptions> clientSelectedMenuItem;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;

    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public AnchorPane getClientsView() {
        if (clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    public ObjectProperty<UserMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getAccountsView() {

        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/User/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/User/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getDepositView() {
        if (depositView == null) {
            try {
                depositView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return depositView;
    }

    public AnchorPane getTransactionsView() {
        if (transactionsView == null) {
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showClientWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/User.fxml"));
            UserController userController = new UserController();
            loader.setController(userController);
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon/bank.png"))));
        stage.setResizable(false);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateAccount.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
