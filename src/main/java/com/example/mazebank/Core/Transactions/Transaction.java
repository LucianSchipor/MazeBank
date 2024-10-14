package com.example.mazebank.Core.Transactions;

public class Transaction {
    private final int transaction_id;
    private final String from_account_id;
    private final String to_account_id;
    private final double amount;
    private final String from_username;
    private final String to_username;
    private final String message;
    private String currency = "";

    public Transaction(int transaction_id, String from_id, String to_id, double amount, String from, String to, String message) {
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
    public String getTo_account_id() {
        return to_account_id;
    }

    public String getFrom_account_id() {
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
