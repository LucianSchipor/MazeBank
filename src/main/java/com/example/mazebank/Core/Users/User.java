package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.CheckingAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private final int id;
    private final String username;
    private final AccountType Role;
    private HashMap<String, CheckingAccount> checkingAccounts;
    private CheckingAccount selectedCheckingAccount;

    public User(int id, String username, String password, int role) {
        this.id = id;
        this.username = username;
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

    public HashMap<String,CheckingAccount> getCheckingAccounts() {
        LinkedHashMap<String, CheckingAccount> checkingAccounts = new LinkedHashMap<>();
        try {
            checkingAccounts = DB_BankAccounts.GetBankAccounts(id);
        }
        catch (Exception e){
            System.out.println("[LOG] - " + e.getMessage());
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
        System.out.println("[LOG] - account selected setted to: " + selectedCheckingAccount.getAccount_id());
        this.selectedCheckingAccount = selectedCheckingAccount;
    }
}
