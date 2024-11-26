package com.example.mazebank.Views;

import com.example.mazebank.Controllers.Admin.AdminController;
import com.example.mazebank.Controllers.Admin.Search.BankAccounts.SearchResultController_BankAccounts;
import com.example.mazebank.Controllers.User.Menu.DashboardController;
import com.example.mazebank.Controllers.User.Transactions.TransactionsController;
import com.example.mazebank.Controllers.User.Menu.UserController;
import com.example.mazebank.Core.Security.Security;
import com.example.mazebank.Core.Users.AccountType;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Views.Admin.Menu.AdminMenuOptions;
import com.example.mazebank.Views.User.Menu.UserMenuOptions;
import com.google.zxing.WriterException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

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
        stage.setResizable(false);
        stage.setTitle(user.getUsername());
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


    public void showQRCode(String barCodeUrl){
        try {
            Image qrImage = Security.createQRCode(barCodeUrl, 300, 300);

            ImageView imageView = new ImageView(qrImage);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, 300 + 50, 300 + 50);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon/bank.png"))));
            stage.setResizable(false);
            stage.setTitle("QR Code");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
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

    public void closeStage(Stage stage) {
        stage.close();
    }
}
