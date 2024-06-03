package com.example.mazebank.Core.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {

    private final String accountNumber;

    private Double balance;

    private String currency;

    Account(String accountNumber, double balance, String currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }


    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
