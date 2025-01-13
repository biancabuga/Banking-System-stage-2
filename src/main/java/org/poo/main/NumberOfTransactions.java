package org.poo.main;

public class NumberOfTransactions implements CashbackStrategy{
    @Override
    public double calculateCashback(double amount, Account account) {
        double cashbackPercentage = 0.0;
        int cashBackDone = 0;

        if (account.getTransactionsFood() == 3 && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = 0.02;
        } else if (account.getTransactionsClothes() == 6 && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = 0.05;
        } else if (account.getTransactionsTech() == 11 && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = 0.10;
        }
        // Returnăm cashback-ul calculat
        return cashbackPercentage * amount;
    }
}
