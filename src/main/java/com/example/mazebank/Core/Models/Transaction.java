package com.example.mazebank.Core.Models;

public class Transaction {
    private final int transaction_id;
    private final int from_account_id;
    private final int to_account_id;
    private final double amount;
    private final String from_username;
    private final String to_username;
    private final String message;
    private String currency = "";

    public Transaction(int transaction_id, int from_id, int to_id, double amount, String from, String to, String message) {
        this.from_account_id = from_id;
        this.to_account_id = to_id;
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
    public int getTo_account_id() {
        return to_account_id;
    }

    public int getFrom_account_id() {
        return from_account_id;
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
}
