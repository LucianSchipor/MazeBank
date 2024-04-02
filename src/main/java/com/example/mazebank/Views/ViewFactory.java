package com.example.mazebank.Views;

import com.example.mazebank.Controllers.Admin.AdminMenuController;
import com.example.mazebank.Controllers.User.UserController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    //Client Views
    private AnchorPane dashboardView;
    private AccountType loginAccountType;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;

    private AnchorPane createClientView;
    private final ObjectProperty<UserMenuOptions> clientSelectedMenuItem;
    private  final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    public ViewFactory(){
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

    public ObjectProperty<UserMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getAccountsView() {

        if(accountsView == null){
            try{
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/User/Accounts.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    return accountsView;
    }

    public  AnchorPane getDashboardView(){
        if(dashboardView == null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/User/Dashboard.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    return dashboardView;
    }

    public AnchorPane getTransactionsView() {
        if(transactionsView == null){
            try{
                transactionsView = new FXMLLoader(getClass().getResource("/Fxml/User/Transactions.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return transactionsView;
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
}

public void showClientWindow(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/User.fxml"));
    UserController userController = new UserController();
    loader.setController(userController);
    createStage(loader);
}

public void showAdminWindow(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
    AdminMenuController controller = new AdminMenuController();
    loader.setController(controller);
    createStage(loader);
}
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        if(createClientView == null){
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateAccount.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
