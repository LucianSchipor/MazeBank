package com.example.mazebank.Core.Transactions;

public class Transaction {
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
}
