package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private final int id;
    private final String username;
    private final AccountType Role;
    private HashMap<String, BankAccount> checkingAccounts;
    private BankAccount selectedBankAccount;

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

    public HashMap<String, BankAccount> getCheckingAccounts() {
        LinkedHashMap<String, BankAccount> checkingAccounts = new LinkedHashMap<>();
        try {
            checkingAccounts = DB_BankAccounts.GetBankAccounts(id);
        }
        catch (Exception e){
            System.out.println("[LOG] - " + e.getMessage());
        }
        return  checkingAccounts;
    }

    public void setCheckingAccounts(HashMap<String, BankAccount> checkingAccount) {
        this.checkingAccounts = checkingAccount;
    }

    public BankAccount getSelectedCheckingAccount() {
        return selectedBankAccount;
    }

    public void setSelectedCheckingAccount(BankAccount selectedBankAccount) {
        System.out.println("[LOG] - account selected setted to: " + selectedBankAccount.getAccount_id());
        this.selectedBankAccount = selectedBankAccount;
    }
}
