package com.example.mazebank.Core.Credit;

import com.example.mazebank.Core.Bank.BankInfo;

public class LoanCalculator {
    public static double calculateMonthlyRate(double principal, int months) {
        double monthlyInterestRate = BankInfo.getInstance().getIntrest() / 12 / 100;

        double monthlyRate = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months))
                / (Math.pow(1 + monthlyInterestRate, months) - 1);

        monthlyRate = Math.round(monthlyRate * 100.0) / 100.0;

        return monthlyRate + 5;
    }
}
