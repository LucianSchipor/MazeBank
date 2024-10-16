package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.CheckingAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private int id;
    private String username;
    private String password;
    private AccountType Role;
    private HashMap<String, CheckingAccount> checkingAccounts;
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

    public HashMap<String,CheckingAccount> getCheckingAccounts() {
        LinkedHashMap<String, CheckingAccount> checkingAccounts = new LinkedHashMap<>();
        try {
            checkingAccounts = DB_BankAccounts.GetBankAccounts(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  checkingAccounts;
    }

    public void setCheckingAccounts(HashMap<String, CheckingAccount> checkingAccount) {
        this.checkingAccounts = checkingAccount;
    }

    public CheckingAccount getSelectedCheckingAccount() {
        return selectedCheckingAccount;
    }

    public void setSelectedCheckingAccount(CheckingAccount selectedCheckingAccount) {
        System.out.println("[LOG] - new bank account selected");
        this.selectedCheckingAccount = selectedCheckingAccount;
    }
}