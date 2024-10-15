package com.example.mazebank.Core.BankAccounts;
public class CheckingAccount extends Account {
    public  CheckingAccount(String accountNumber, double balance, String currency){
        super(accountNumber, balance, currency);
    }
    private String account_id;

    public CheckingAccount(){
        super("0", 0, "NOC");
    }

    public String getAccount_id() {
        return account_id;
    }
    public void setAccount_id(String account_id) {
            this.account_id = account_id;
}
}
