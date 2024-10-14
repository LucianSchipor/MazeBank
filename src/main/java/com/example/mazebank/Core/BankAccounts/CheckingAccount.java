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
            String formattedAccountId = String.format("%s %s %s %s",
                    account_id.substring(0, 4),
                    account_id.substring(4, 8),
                    account_id.substring(8, 12),
                    account_id.substring(12, 16));
            this.account_id = formattedAccountId;
}
}
