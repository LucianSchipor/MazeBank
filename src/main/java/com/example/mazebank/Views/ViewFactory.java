package com.example.mazebank.Views;

import com.example.mazebank.Controllers.Admin.AdminController;
import com.example.mazebank.Controllers.Admin.Search.BankAccounts.SearchResultController_BankAccounts;
import com.example.mazebank.Controllers.User.Menu.DashboardController;
import com.example.mazebank.Controllers.User.TempUser.TempUserController;
import com.example.mazebank.Controllers.User.TempUser.TempUserDashboardController;
import com.example.mazebank.Controllers.User.Transactions.TransactionsController;
import com.example.mazebank.Controllers.User.Menu.UserController;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Views.Admin.Menu.AdminMenuOptions;
import com.example.mazebank.Views.User.Menu.TempUserMenuOptions;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class ViewFactory {

    private AnchorPane dashboardView;
    private AccountType loginAccountType;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;
    private AnchorPane createClientView;
    private AnchorPane searchView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private AnchorPane formsView;
    private AnchorPane tempUserDashboard;
    private AnchorPane confirmation_alert;
    private AnchorPane error_alert;
    private AnchorPane info_alert;
    private final ObjectProperty<UserMenuOptions> clientSelectedMenuItem;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private final ObjectProperty<TempUserMenuOptions> tempUserSelectedMenuItem;
    private Scene previousWindow;

    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        this.tempUserSelectedMenuItem = new SimpleObjectProperty<>();
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
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Accounts/Accounts.fxml")).load();
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
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/User/BankAccounts/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    public AnchorPane getDashboardView() {
            try {
                var dashboardV = new FXMLLoader(getClass().getResource("/Fxml/User/Menu/Dashboard.fxml"));
                dashboardV.setController(new DashboardController());
                dashboardView = dashboardV.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return dashboardView;
    }

    public AnchorPane getDepositView() {
        if (depositView == null) {
            try {
                depositView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit/Deposit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return depositView;
    }

    public AnchorPane getTransactionsView() {
            try {
                var tV = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions/Transactions.fxml"));
                tV.setController(new TransactionsController());
                transactionsView = tV.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return transactionsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showAdminOptionsForUserWindow(User user){
        SearchResultController_BankAccounts controller = new SearchResultController_BankAccounts(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Search/SearchResult/BankAccounts/SearchResult_BankAccounts.fxml"));
        loader.setController(controller);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon/bank.png"))));
        stage.setResizable(true);
        stage.setTitle(user.getUsername() + "'s Bank Accounts");
        stage.show();
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


    public void show2FAWindow(Scene previousScene){
        this.previousWindow = previousScene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/2FA.fxml"));
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void showPreviousWindow() {
        Stage stage = new Stage();
        stage.setScene(this.previousWindow);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon/bank.png"))));
        stage.setResizable(false);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showTempUserDashboard(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/TempUser/TempUser.fxml"));
        TempUserController controller = new TempUserController();
        loader.setController(controller);
        createStage(loader);
    }
    public void showRegisterFormWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showConfirmationAlertWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Alerts/Confirmation_Alert.fxml"));
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showInfoAlertWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Alerts/Info_Alert.fxml"));
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showErrorAlertWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Alerts/Fail_Alert.fxml"));
            createStage(loader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            System.out.println("[LOG][CreateStage] - " + e.getCause());
            System.out.println("[LOG][CreateStage] - " + e.getLocalizedMessage());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon/bank.png"))));
        stage.setResizable(true);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    private void createStage(FXMLLoader loader, String title) {
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
        stage.setTitle(title);
        stage.show();
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public ObjectProperty<TempUserMenuOptions> getTempUserSelectedMenuItem() {
        return tempUserSelectedMenuItem;
    }

    public AnchorPane getCreateView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Create/Create.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public AnchorPane getSearchView() {
        if (searchView == null) {
            try {
                searchView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Search/Search.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchView;
    }

    public AnchorPane getFormsView() {
        if (formsView == null) {
            try {
                formsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Forms/Forms.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formsView;
    }

    public AnchorPane getTempDashboardView() {
            try {
                tempUserDashboard = new FXMLLoader(getClass().getResource("/Fxml/User/TempUser/Menu/TempUserDashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return tempUserDashboard;
    }

    public AnchorPane getRegisterFormWindow() {
        if (formsView == null) {
            try {
                formsView = new FXMLLoader(getClass().getResource("/Fxml/User/Forms/RegisterForm.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formsView;
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
