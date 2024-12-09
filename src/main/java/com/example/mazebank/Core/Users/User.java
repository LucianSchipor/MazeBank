package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Repositories.BankAccounts.DB_BankAccounts;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private final int id;
    private final String username;
    private final AccountType Role;
    private LocalDateTime FA_Verification_Time;
    private String Email;
    private Boolean FA_Enabled = false;
    private boolean FA_Verified = false;
    private String FA_Key = "";
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

    public void setFA_Enabled(Boolean FA_Enabled) {
        this.FA_Enabled = FA_Enabled;
    }

    public boolean isFA_Enabled() {
        return FA_Enabled;
    }


    public boolean isFA_Verified() {
//    Pe baza timpului cand s-a efectuat ultima verificare, stocat in BD in 2FA_Verification_Time
//    Vad daca aceasta verificare a fost factuta acum mai mult de 15 minute.
//    Daca da, cer iar sa-si verifice codul

        return Duration.between(FA_Verification_Time, LocalDateTime.now()).toMinutes() <= 15;
    }

    public void setFA_Verified(boolean FA_Verified) {
        this.FA_Verified = FA_Verified;
    }

    public void setFA_Enabled(boolean FA_Enabled) {
        this.FA_Enabled = FA_Enabled;
    }

    public String getFA_Key() {
        return FA_Key;
    }

    public void setFA_Key(String FA_Key) {
        this.FA_Key = FA_Key;
    }

    public void setFA_Verification_Time(LocalDateTime FA_Verification_Time) {
        this.FA_Verification_Time = FA_Verification_Time;
    }
}
