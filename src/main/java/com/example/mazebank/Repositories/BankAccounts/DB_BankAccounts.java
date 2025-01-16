package com.example.mazebank.Repositories.BankAccounts;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Security.Encryption.EncryptionManager;
import com.example.mazebank.Core.Security.KeyManager.KeyManager;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.scene.control.Alert;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DB_BankAccounts {
    @SuppressWarnings("SqlNoDataSourceInspection")
    public static LinkedHashMap<String, BankAccount> GetBankAccounts(int user_id) {
        PreparedStatement psCheckUserExists;
        LinkedHashMap<String, BankAccount> accounts = new LinkedHashMap<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");

            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");
            psCheckUserExists.setInt(1, user_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                var ba = createBankAccountFromDB(resultSet);
                accounts.put(ba.getAccount_id(), ba);
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        }
        return accounts;
    }


    private static BankAccount createBankAccountFromDB(ResultSet resultSet) throws Exception {
        try {
            String bankAccount_id = EncryptionManager.decrypt(resultSet.getString("account_id"), KeyManager.loadKey());
            double balance = Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("account_balance"), KeyManager.loadKey()));
            String currency = EncryptionManager.decrypt(resultSet.getString("account_currency"), KeyManager.loadKey());
            String CVV = EncryptionManager.decrypt(resultSet.getString("cvv"), KeyManager.loadKey());
            Date date = resultSet.getDate("expire_date");
            String IBAN = EncryptionManager.decrypt(resultSet.getString("iban"), KeyManager.loadKey());
            BankAccount ba = new BankAccount(bankAccount_id, balance, currency, date, CVV);
            ba.setIBAN(IBAN);
            ba.setAccount_id(bankAccount_id);
            ba.setTransactions(DB_Transactions.getBankAccountTransactions(ba.getIBAN()));
            return ba;
        } catch (Exception exception) {
            System.out.println("[LOG][BankAccounts] " + exception.getMessage());
            throw new Exception("[LOG][BankAccounts] " + exception.getMessage());
        }

    }

    public static void updateBankAccountsAfterTransaction(String sender, Double amount, String receiver) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement psInsertTransaction;
        ResultSet resultSet;
        try {
            assert connection != null;
            PreparedStatement querry = connection.prepareStatement("SELECT account_balance FROM bank_accounts WHERE iban = ?");
            querry.setString(1, EncryptionManager.encrypt(sender, KeyManager.loadKey()));
            resultSet = querry.executeQuery();
            if (resultSet.next()) {
                var account_balance = Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("account_balance"), KeyManager.loadKey()));
                psInsertTransaction = connection.prepareStatement(
                        "UPDATE bank_accounts " +
                                "SET account_balance = ? " +
                                "WHERE iban = ?");
                psInsertTransaction.setString(1, EncryptionManager.encrypt(String.valueOf(account_balance - amount), KeyManager.loadKey()));
                psInsertTransaction.setString(2, sender);
                psInsertTransaction.executeUpdate();
            }

            psInsertTransaction = connection.prepareStatement(
                    "SELECT account_currency from bank_accounts " +
                            "WHERE iban = ?");
            psInsertTransaction.setString(1, EncryptionManager.encrypt(receiver, KeyManager.loadKey()));
            resultSet = psInsertTransaction.executeQuery();
            String currency = "";
            while (resultSet.next()) {
                currency = EncryptionManager.decrypt(resultSet.getString("account_currency"), KeyManager.loadKey());
            }

            querry = connection.prepareStatement("SELECT account_balance FROM bank_accounts WHERE iban = ?");
            querry.setString(1, EncryptionManager.encrypt(sender, KeyManager.loadKey()));
            resultSet = querry.executeQuery();
            if (resultSet.next()) {
                var account_balance = Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("account_balance"), KeyManager.loadKey()));
                psInsertTransaction = connection.prepareStatement(
                        "UPDATE bank_accounts " +
                                "SET account_balance = ? " +
                                "WHERE iban = ?");
                Double newAmount = currencyConversion(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getCurrency(), currency, amount);
                psInsertTransaction.setString(1, EncryptionManager.encrypt(String.valueOf(account_balance + newAmount), KeyManager.loadKey()));
                psInsertTransaction.setString(2, EncryptionManager.encrypt(receiver, KeyManager.loadKey()));
                psInsertTransaction.executeUpdate();
            }
        } catch (Exception exception) {
            System.out.println("[LOG] - " + exception.getMessage());
        }
    }

    private static String generateIBAN() {
        String IBAN;
        String RO = "RO";
        Random random = new Random();
        int randomTwoDigitNumber = 10 + random.nextInt(90);
        String RO_NR = String.valueOf(randomTwoDigitNumber);
        String MAZE = "MAZE";
        String defaultNumbers = "00000600";
        int lowerBound = 10000000;
        int upperBound = 90000000;
        int randomEightDigitNumber = lowerBound + random.nextInt(upperBound);
        String lastNumbers = String.valueOf(randomEightDigitNumber);
        IBAN = RO + RO_NR + MAZE + defaultNumbers + lastNumbers;

        if (searchBankAccountByIBAN(IBAN) != null) {
            generateIBAN();
        }
        return IBAN;
    }

    private static String generateNewAccountNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        number.append(random.nextInt(9) + 1);
        for (int i = 1; i < 16; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }

    public static String generateCVV() {
        Random random = new Random();
        return String.valueOf(100 + random.nextInt(900));
    }

    public static void createBankAccount(int user_id, String currency) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement psCheckUserExists;
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("INSERT INTO bank_accounts (account_id, account_balance, account_currency, user_id, pin, iban, cvv, expire_date) VALUES (?,?,?,?,?,?,?,?)");
            psCheckUserExists.setString(1, EncryptionManager.encrypt(generateNewAccountNumber(), KeyManager.loadKey()));
            psCheckUserExists.setString(2, EncryptionManager.encrypt(String.valueOf(0), KeyManager.loadKey()));
            psCheckUserExists.setString(3, EncryptionManager.encrypt(currency, KeyManager.loadKey()));
            psCheckUserExists.setInt(4, user_id);
            psCheckUserExists.setString(5, EncryptionManager.encrypt("0000", KeyManager.loadKey()));
            psCheckUserExists.setString(6, EncryptionManager.encrypt(generateIBAN(), KeyManager.loadKey()));
            psCheckUserExists.setString(7, EncryptionManager.encrypt(generateCVV(), KeyManager.loadKey()));
            psCheckUserExists.setDate(8, Date.valueOf(LocalDate.now().plusYears(4)));

            int rowsAffected = psCheckUserExists.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully created user account.");
            }
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
    }

    public static BankAccount searchBankAccountByIBAN(String IBAN) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().getConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement querry;
        ResultSet resultSet;
        assert connection != null;

        try {
            querry = connection.prepareStatement("SELECT * FROM bank_accounts where IBAN = ?");
            querry.setString(1, EncryptionManager.encrypt(IBAN, KeyManager.loadKey()));
            resultSet = querry.executeQuery();
            if (resultSet.next()) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccount_id(
                        resultSet.getString("account_id"));
                bankAccount.setBalance(
                        Double.parseDouble(EncryptionManager.decrypt(resultSet.getString("account_balance"), KeyManager.loadKey())));
                bankAccount.setCurrency(
                        EncryptionManager.decrypt(resultSet.getString("account_currency"), KeyManager.loadKey()));
                bankAccount.setIBAN(IBAN);
                return bankAccount;
            }
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        return null;
    }

    private static Double currencyConversion(String currency, String currency2, Double amount) {
        Map<String, Double> exchangeRates = new HashMap<>();
        // Rates are in RON -> 1 DOL - 4.56 RON
        exchangeRates.put("USD", 4.56);
        exchangeRates.put("EUR", 4.98);
        exchangeRates.put("CHF", 5.29);
        exchangeRates.put("GBP", 5.97);
        exchangeRates.put("RON", 1.0);

        double amountInRON = amount * exchangeRates.get(currency);
        return amountInRON / exchangeRates.get(currency2);
    }
}
