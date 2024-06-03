package com.example.mazebank.Core.Models;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private AccountType Role;
    private CheckingAccount checkingAccount;
    private List<CheckingAccount> checkingAccounts;


    public User(int id, String username, String password, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        Role = AccountType.values()[role];
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId(){
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getRole() {
        return Role;
    }

    public void setRole(AccountType role) {
        Role = role;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public List<CheckingAccount> getCheckingAccounts() {
        return checkingAccounts;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }
    public void setCheckingAccounts(List<CheckingAccount> checkingAccount) {
        this.checkingAccounts = checkingAccount;
    }
}
