package com.example.mazebank.Core.Bank;
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
