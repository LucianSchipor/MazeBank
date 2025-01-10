package com.example.mazebank.Repositories.BankAccounts;

import com.example.mazebank.Core.BankAccounts.BankAccount;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.DBUtils.DB_ConnectionManager;
import com.example.mazebank.Repositories.Transactions.DB_Transactions;
import javafx.scene.control.Alert;

import javax.xml.transform.Result;
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
                String account_id = resultSet.getString("account_id");
                double balance = resultSet.getDouble("account_balance");
                String currency = resultSet.getString("account_currency");
                String CVV = resultSet.getString("cvv");
                Date date = resultSet.getDate("expire_date");
                String IBAN = resultSet.getString("IBAN");

                var newBankAccount = new BankAccount(account_id, balance, currency, date, CVV);
                newBankAccount.setAccount_id(resultSet.getString("account_id"));
                newBankAccount.setTransactions(DB_Transactions.GetBankAccountTransactions(account_id));
                newBankAccount.setIBAN(IBAN);
                accounts.put(account_id, newBankAccount);
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

    public static BankAccount GetBankAccountByAccountId(String account_id) {
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        BankAccount foundBankAccount = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mazebank", "root", "ariseu123");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE account_id = ?");

            psCheckUserExists.setString(1, account_id);
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                String bankAccount_id = resultSet.getString("account_id");
                double balance = resultSet.getDouble("account_balance");
                String currency = resultSet.getString("account_currency");
                String CVV = resultSet.getString("cvv");
                Date date = resultSet.getDate("expire_date");
                String IBAN = resultSet.getString("IBAN");

                foundBankAccount = new BankAccount(bankAccount_id, balance, currency, date, CVV);
                foundBankAccount.setAccount_id(resultSet.getString("account_id"));
//                foundBankAccount.setTransactions(DB_Transactions.GetBankAccountTransactions(account_id));
                foundBankAccount.setIBAN(IBAN);
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
        return foundBankAccount;
    }

    // Used for set the local Checking Account with updated from database Checking Account.
    // Returns same Checkin Account but updated
    public static void Local_UpdateBankAccountsAfterTransaction(BankAccount account) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        BankAccount newBankAccount = null;
        try {
            assert connection != null;
            psCheckUserExists = connection.prepareStatement("SELECT * FROM bank_accounts WHERE account_id = ?");
            psCheckUserExists.setString(1, account.getAccount_id());
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                double balance = resultSet.getDouble("account_balance");
                account.setTransactions(DB_Transactions.GetBankAccountTransactions(account.getAccount_id()));
                account.setBalance(balance);
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
    }

    public static void DB_UpdateBankAccountsAfterTransaction(String sender, Double amount, String receiver) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement psInsertTransaction;
        PreparedStatement psGetBankAccount;
        ResultSet resultSet;
        Double sender_balance = (double) 0;
        Double receiver_balance = (double) 0;
        try {
            assert connection != null;
            psInsertTransaction = connection.prepareStatement(
                    "UPDATE bank_accounts " +
                            "SET account_balance = account_balance - ? " +
                            "WHERE account_id = ?");
            psInsertTransaction.setDouble(1, amount);
            psInsertTransaction.setString(2, sender);
            psInsertTransaction.executeUpdate();

            psInsertTransaction = connection.prepareStatement(
                    "SELECT account_currency from bank_accounts " +
                            "WHERE account_id = ?");
            psInsertTransaction.setString(1, receiver);
            resultSet = psInsertTransaction.executeQuery();
            String currency = "";
            while (resultSet.next()) {
                currency = resultSet.getString("account_currency");
            }

            psInsertTransaction = connection.prepareStatement(
                    "UPDATE bank_accounts " +
                            "SET account_balance = account_balance + ? " +
                            "WHERE account_id = ?");

            Double newAmount = Currency_Conversion(UserLoggedIn.getInstance().getLoggedInUser().getSelectedCheckingAccount().getCurrency(), currency, amount);
            psInsertTransaction.setDouble(1, newAmount);
            psInsertTransaction.setString(2, receiver);
            psInsertTransaction.executeUpdate();
        } catch (Exception exception) {
            System.out.println("[LOG] - " + exception.getMessage());
        }
    }


    private static String GenerateIBAN() throws SQLException {
        String IBAN = "";
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

        if(SearchBankAccountByIBAN(IBAN) != null){
            GenerateIBAN();
        }
        return IBAN;
    }


    private static String GenerateNewAccountNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        number.append(random.nextInt(9) + 1);
        for (int i = 1; i < 16; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }

    public static String GenerateCVV() {
        Random random = new Random();
        return String.valueOf(100 + random.nextInt(900));
    }

    public static void CreateBankAccount(int user_id, String currency) {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;
        try {
            psCheckUserExists = connection.prepareStatement("INSERT INTO bank_accounts (account_id, account_balance, account_currency, user_id, pin, iban, cvv, expire_date) VALUES (?,?,?,?,?,?,?,?)");
            psCheckUserExists.setString(1, GenerateNewAccountNumber());
            psCheckUserExists.setFloat(2, 0);
            psCheckUserExists.setString(3, currency);
            psCheckUserExists.setInt(4, user_id);
            psCheckUserExists.setString(5, "0000");
            psCheckUserExists.setString(6, GenerateIBAN());
            psCheckUserExists.setString(7, GenerateCVV());
            psCheckUserExists.setDate(8, Date.valueOf(LocalDate.now().plusYears(4)));

            int rowsAffected = psCheckUserExists.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully created user account.");
            }
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
    }

    public static void GenerateNewIBANForAllAccounts(){
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement psCheckUserExists;
        ResultSet resultSet = null;
        try {
            PreparedStatement psSelectAccounts = connection.prepareStatement("SELECT account_id FROM bank_accounts");
            ResultSet rs = psSelectAccounts.executeQuery();

            PreparedStatement psUpdateIBAN = connection.prepareStatement("UPDATE bank_accounts SET IBAN = ? WHERE account_id = ?");

            while (rs.next()) {
                String accountId = rs.getString("account_id");
                String newIBAN = GenerateIBAN();

                psUpdateIBAN.setString(1, newIBAN);
                psUpdateIBAN.setString(2, accountId);

                int rowsAffected = psUpdateIBAN.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Successfully updated IBAN for account ID: " + accountId);
                }
            }

            // Închidem resursele
            rs.close();
            psSelectAccounts.close();
            psUpdateIBAN.close();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
    }

    public static BankAccount SearchBankAccountByIBAN(String IBAN) throws SQLException {
        Connection connection = null;
        try {
            connection = DB_ConnectionManager.getInstance().GetConnection();
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        PreparedStatement querry;
        ResultSet resultSet;
        assert connection != null;

        try {
            querry = connection.prepareStatement("SELECT * FROM bank_accounts where IBAN = ?");
            querry.setString(1, IBAN);
            resultSet = querry.executeQuery();
            while(resultSet.next()) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccount_id(resultSet.getString("account_id"));
                bankAccount.setBalance(resultSet.getDouble("account_balance"));
                bankAccount.setCurrency(resultSet.getString("account_currency"));
                bankAccount.setTransactions(DB_Transactions.GetBankAccountTransactions(IBAN));
                bankAccount.setIBAN(IBAN);
                return bankAccount;
            }
        } catch (Exception e) {
            System.out.println("[LOG] - " + e.getMessage());
        }
        return null;
    }
    private static Double Currency_Conversion(String currency, String currency2, Double amount) {
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
