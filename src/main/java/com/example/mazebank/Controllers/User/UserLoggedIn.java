package com.example.mazebank.Controllers.User;

import com.example.mazebank.Core.Models.CheckingAccount;
import com.example.mazebank.Core.Models.User;

public class UserLoggedIn {
    private static UserLoggedIn instance;
    private User loggedInUser;
    private CheckingAccount checkingAccount;

    private UserLoggedIn() {}

    public static synchronized UserLoggedIn getInstance() {
        if (instance == null) {
            instance = new UserLoggedIn();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }



}

