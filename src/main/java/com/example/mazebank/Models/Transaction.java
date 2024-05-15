package com.example.mazebank.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
private final StringProperty sender;
private final StringProperty receiver;
private final DoubleProperty amount;
private final ObjectProperty<LocalDate> date;
private final StringProperty message;

public Transaction(String sender, String receiver, double amount, LocalDate date, String message) {
    this.sender = new SimpleStringProperty(this, "sender", sender);
    this.receiver = new SimpleStringProperty(this, "Receiver", receiver);
    this.amount = new SimpleDoubleProperty(this, "Amount", amount);
    this.date = new SimpleObjectProperty<>(this, "Date", date);
    this.message = new SimpleStringProperty(this, "Message", message);
}


    public String getReceiver() {
        return receiver.get();
    }

    public StringProperty receiverProperty() {
        return receiver;
    }

    public StringProperty getSender() {
        return this.sender;
    }


    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }
}