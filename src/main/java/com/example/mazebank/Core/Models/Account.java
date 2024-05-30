package com.example.mazebank.Core.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {

    private final StringProperty accountNumber;

    private final DoubleProperty balance;

    private final StringProperty currency;

    Account(String accountNumber, double balance, String currency){
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
        this.currency = new SimpleStringProperty(this, "Currency", currency);
    }


    public StringProperty accountNumberProperty() {return accountNumber;}

    public DoubleProperty balanceProperty() {return balance;}

    public String getCurrency() {
        return currency.get();
    }
}
