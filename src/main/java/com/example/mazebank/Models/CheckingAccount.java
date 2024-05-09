package com.example.mazebank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account {
    private final IntegerProperty transactionLimit;
    public  CheckingAccount(String owner, String accountNumber, double balance, int limit){
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", limit);
    }

    public IntegerProperty transactionLimitProperty(){
        return transactionLimit;
    }
}
