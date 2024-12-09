package com.example.mazebank.Core.BankAccounts;

import java.util.Date;

public class BankAccount extends Account {
    public BankAccount(String accountNumber, double balance, String currency, Date expire_date, String CVV) {
        super(accountNumber, balance, currency);
        this.expire_date = expire_date;
        this.CVV = CVV;
    }
    private String account_id;
    private Date expire_date;
    private String CVV;

    public BankAccount(){
        super("0", 0, "NOC");
    }

    public String getAccount_id() {
        return account_id;
    }
    public void setAccount_id(String account_id) {
            this.account_id = account_id;
}
    public String getCVV() {
        return this.CVV;
    }

    public Date getExpire_date() {
        return this.expire_date;
    }
}