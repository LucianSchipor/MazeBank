package com.example.mazebank.Core.BankAccounts;

import com.example.mazebank.Core.Transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private final String accountNumber;

    private List<Transaction> transactions = new ArrayList<>();
    private Double balance;

    private String currency;

    Account(String accountNumber, double balance, String currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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
