package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private final int id;
    private final String username;
    private final AccountType Role;
    private String Email;
    private Boolean FA_Enabled = false;
    private boolean FA_Verified = false;
    private HashMap<String, BankAccount> checkingAccounts;
    private BankAccount selectedBankAccount;

    public User(int id, String username, String password, int role, String email) {
        this.id = id;
        this.username = username;
        Role = AccountType.values()[role];
        Email = email;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email =  email;
    }

    public Boolean getFA_Enabled() {
        return FA_Enabled;
    }

    public void setFA_Enabled(Boolean FA_Enabled) {
        this.FA_Enabled = FA_Enabled;
    }

    public boolean isFA_Verified() {
        return FA_Verified;
    }

    public void setFA_Verified(boolean FA_Verified) {
        this.FA_Verified = FA_Verified;
    }
}
