package com.example.mazebank.Core.Transactions;

import com.example.mazebank.Core.BankAccounts.BankAccount;

import java.sql.Timestamp;

public class Transaction {
    private BankAccount sender_BankAccount;
    private Timestamp timestamp;
    private BankAccount reciever_BankAccount;
    private final int transaction_id;
    private final String sender;
    private final String receiver;
    private final double amount;
    private final String from_username;
    private final String to_username;
    private final String message;
    private String currency = "";

    public Transaction(int transaction_id, String sender, String receiver, double amount, String from, String to, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.from_username = from;
        this.to_username = to;
        this.transaction_id = transaction_id;
        this.message = message;
    }

    public String getCurrency(){
        if(currency == null)
            return "NOC";
        if(currency.isEmpty())
            return "NOC";
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }
    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public double getAmount() {
        return amount;
    }

    public String getFrom_username() {
        return from_username;
    }

    public String getTo_username() {
        return to_username;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getMessage() {
        return message;
    }

    public BankAccount getSender_BankAccount() {
        return sender_BankAccount;
    }

    public void setSender_BankAccount(BankAccount sender_BankAccount) {
        this.sender_BankAccount = sender_BankAccount;
    }

    public BankAccount getReciever_BankAccount() {
        return reciever_BankAccount;
    }

    public void setReciever_BankAccount(BankAccount reciever_BankAccount) {
        this.reciever_BankAccount = reciever_BankAccount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
