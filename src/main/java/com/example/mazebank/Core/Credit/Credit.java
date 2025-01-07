package com.example.mazebank.Core.Credit;

public class Credit {
    private int user_id;
    private int credit_id;
    private float credit_total_sum;
    private int credit_period;
    private String credit_currency;
    private float credit_monthly_rate;
    private float credit_intrest;

    public Credit(int user_id, float credit_total_sum, int credit_period, String credit_currency, float credit_monthly_rate, float credit_intrest) {
        this.user_id = user_id;
        this.credit_total_sum = credit_total_sum;
        this.credit_period = credit_period;
        this.credit_currency = credit_currency;
        this.credit_monthly_rate = credit_monthly_rate;
        this.credit_intrest = credit_intrest;
    }

    public int getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(int credit_id) {
        this.credit_id = credit_id;
    }

    public float getCredit_total_sum() {
        return credit_total_sum;
    }

    public void setCredit_total_sum(float credit_total_sum) {
        this.credit_total_sum = credit_total_sum;
    }

    public int getCredit_period() {
        return credit_period;
    }

    public void setCredit_period(int credit_period) {
        this.credit_period = credit_period;
    }

    public String getCredit_currency() {
        return credit_currency;
    }

    public void setCredit_currency(String credit_currency) {
        this.credit_currency = credit_currency;
    }

    public float getCredit_monthly_rate() {
        return credit_monthly_rate;
    }

    public void setCredit_monthly_rate(float credit_monthly_rate) {
        this.credit_monthly_rate = credit_monthly_rate;
    }

    public float getCredit_intrest() {
        return credit_intrest;
    }

    public void setCredit_intrest(float credit_intrest) {
        this.credit_intrest = credit_intrest;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
