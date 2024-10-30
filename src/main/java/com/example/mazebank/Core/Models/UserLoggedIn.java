package com.example.mazebank.Core.Models;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Users.User;

public class UserLoggedIn {
    private static UserLoggedIn instance;
    private User loggedInUser;
    private BankAccount bankAccount;

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

