package com.example.mazebank.Core.Models;

import com.example.mazebank.Repositories.DBUtils.DB_BankAccounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private AccountType Role;
    private HashMap<Integer, CheckingAccount> checkingAccounts;
    private CheckingAccount selectedCheckingAccount;

    public User(int id, String username, String password, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        Role = AccountType.values()[role];
    }

    public String getUsername() {
        return username;
    }


    public int getUserId(){
        return id;
    }

    public AccountType getRole() {
        return Role;
    }


    public void setCheckingAccounts(){

    }

    public HashMap<Integer,CheckingAccount> getCheckingAccounts() {
        return DB_BankAccounts.GetBankAccounts(id);
    }

    public void setCheckingAccounts(HashMap<Integer, CheckingAccount> checkingAccount) {
        this.checkingAccounts = checkingAccount;
    }

    public CheckingAccount getSelectedCheckingAccount() {
        return selectedCheckingAccount;
    }

    public void setSelectedCheckingAccount(CheckingAccount selectedCheckingAccount) {
        this.selectedCheckingAccount = selectedCheckingAccount;
    }
}
