package com.example.mazebank.Views;

import com.example.mazebank.Controllers.Admin.AdminController;
import com.example.mazebank.Controllers.Admin.Search.BankAccounts.SearchResultController_BankAccounts;
import com.example.mazebank.Controllers.User.Dashboard.DashboardController;
import com.example.mazebank.Controllers.User.TempUser.TempUserController;
import com.example.mazebank.Controllers.User.Transactions.TransactionsController;
import com.example.mazebank.Controllers.User.Menu.UserController;
import com.example.mazebank.Controllers.User.Transactions.TransactionsWithOnePers.TransactionsWithOnePersController;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Views.Admin.Menu.AdminMenuOptions;
import com.example.mazebank.Views.User.Forms.FormsMenuOptions;
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
    private AnchorPane loginWindow;
    private AnchorPane transactionsView;
    private AnchorPane transWithOnePersView;
    private AnchorPane accountsView;
    private AnchorPane createClientView;
    private AnchorPane searchView;
    private AnchorPane userFormsView;
    private AnchorPane clientsView;
    private AnchorPane addFundsView;
    private AnchorPane depositView;
    private AnchorPane formsView;
    private AnchorPane tempUserDashboard;
    private AnchorPane formsAccountsView;
    private AnchorPane formsCreditsView;
    private final ObjectProperty<UserMenuOptions> userSelectedMenuItem;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private final ObjectProperty<TempUserMenuOptions> tempUserSelectedMenuItem;
    private final ObjectProperty<FormsMenuOptions> formsMenuSelectedItem;
    private Scene previousWindow;

    public ViewFactory() {
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        this.tempUserSelectedMenuItem = new SimpleObjectProperty<>();
        this.formsMenuSelectedItem = new SimpleObjectProperty<>();
    }

    public AnchorPane getClientsView() {
        try {
            clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Accounts/Accounts.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientsView;
    }

    public ObjectProperty<FormsMenuOptions> getFormsMenuSelectedMenuItem() {
        return formsMenuSelectedItem;
    }

    public ObjectProperty<UserMenuOptions> getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    public AnchorPane getAccountsView() {

        try {
            accountsView = new FXMLLoader(getClass().getResource("/Fxml/User/BankAccounts/Accounts.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountsView;
    }

    public AnchorPane getAddFundsView() {

        try {
            addFundsView = new FXMLLoader(getClass().getResource("/Fxml/User/Add Funds/Add_Funds.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFundsView;
    }

    public AnchorPane getUserFormsView() {

        try {
            userFormsView = new FXMLLoader(getClass().getResource("/Fxml/User/Forms/Forms.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userFormsView;
    }

    public AnchorPane getDashboardView() {
        try {
            var dashboardV = new FXMLLoader(getClass().getResource("/Fxml/User/Dashboard.fxml"));
            dashboardV.setController(new DashboardController());
            dashboardView = dashboardV.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dashboardView;
    }

    public AnchorPane getLoginWindow() {
        try {
            loginWindow = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit/Deposit.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginWindow;
    }

    public AnchorPane getDepositView() {
        try {
            depositView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit/Deposit.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
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

    public AnchorPane getTransactionsRetransferView(String sender, String receiver) {
        try {
            var tV = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions/Transactions.fxml"));
            tV.setController(new TransactionsController(sender, receiver));
            transWithOnePersView = tV.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transWithOnePersView;
    }


    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showTransactionsWithOnePersWindow() {
        TransactionsWithOnePersController controller = new TransactionsWithOnePersController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions/TransactionsWithOnePers.fxml"));

        loader.setController(controller);
        var loggeduser = UserLoggedIn.getInstance().getLoggedInUser();
        if (!loggeduser.getUsername().equals(UserLoggedIn.getInstance().getRetransferSelectedTransaction().getTo_username())) {
            controller.setTitle("Your transactions with " + UserLoggedIn.getInstance().getRetransferSelectedTransaction().getTo_username());
        } else
            controller.setTitle("Your transactions with " + UserLoggedIn.getInstance().getRetransferSelectedTransaction().getFrom_username());

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
        stage.show();
    }


    public void showAdminOptionsForUserWindow(User user) {
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

    public void show2FAWindow(Scene previousScene) {
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

    public void showUserWindow(User user) {
        if (user.getRole().equals(AccountType.ADMIN)) {
            showAdminWindow();
        } else if (user.getRole().equals(AccountType.CLIENT)) {
            showClientWindow();
        } else {
            showTempUserDashboard();
        }
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showTempUserDashboard() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/TempUser/TempUser.fxml"));
        TempUserController controller = new TempUserController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showRegisterFormWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/SignUp/SignUp.fxml"));
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

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public ObjectProperty<TempUserMenuOptions> getTempUserSelectedMenuItem() {
        return tempUserSelectedMenuItem;
    }

    public AnchorPane getCreateView() {
        try {
            createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Create/Create.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createClientView;
    }

    public AnchorPane getSearchView() {
        try {
            searchView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Search/Search.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchView;
    }

    public AnchorPane getFormsView() {
        try {
            formsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Forms/Forms.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            formsView = new FXMLLoader(getClass().getResource("/Fxml/User/SignUp/SignUpForm.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formsView;
    }

    public AnchorPane getFormsAccountView() {
        try {
            formsAccountsView = new FXMLLoader(getClass().getResource("/Fxml/User/Forms/Account/Forms_Account.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formsAccountsView;
    }

    public AnchorPane getFormsCreditsView() {
        try {
            formsCreditsView = new FXMLLoader(getClass().getResource("/Fxml/User/Forms/Credits/Forms_Credits.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formsCreditsView;
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
