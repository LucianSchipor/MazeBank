package com.example.mazebank.Core.Users;

import com.example.mazebank.Core.BankAccounts.Account;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client (String fName, String lName, String pAddress, Account cAccount, Account sAccount, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "FirstName", fName);
        this.lastName = new SimpleStringProperty(this, "LastName", lName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", pAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", cAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", sAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
    }

    public StringProperty firstNameProperty() {return firstName;}
    public StringProperty lastNameProperty() {return lastName;}
    public StringProperty pAdressProperty(){return payeeAddress;}
    public ObjectProperty<Account> checkingAccountProperty(){return checkingAccount;}
    public ObjectProperty<Account> savingsAccountProperty(){return savingsAccount;}
    public ObjectProperty<LocalDate> dateCreatedProperty() {return dateCreated;}
}
