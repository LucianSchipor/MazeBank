package com.example.mazebank.Core.Bank;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.User;
import com.example.mazebank.Repositories.BankInfo.DB_BankInfo;

public class BankInfo {
    private static BankInfo instance;

    private float intrest;
    private BankInfo() {
    }

    public static synchronized BankInfo getInstance() {
        if (instance == null) {
            instance = new BankInfo();
        }
        return instance;
    }

    public float getIntrest() {
        return intrest;
    }

    public void setIntrest(float intrest) {
        this.intrest = intrest;
    }
}
