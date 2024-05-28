package com.example.mazebank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {

    private final StringProperty accountNumber;

    private final DoubleProperty balance;

    Account(String accountNumber, double balance){
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
    }


    public StringProperty accountNumberProperty() {return accountNumber;}

    public DoubleProperty balanceProperty() {return balance;}

}
