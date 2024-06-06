package com.example.mazebank.Core.Models;
public class CheckingAccount extends Account {
    public  CheckingAccount(String accountNumber, double balance, String currency){
        super(accountNumber, balance, currency);
    }
    private int account_id;

    public CheckingAccount(){
        super("0", 0, "NOC");
    }

    public int getAccount_id() {
        return account_id;
    }
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
