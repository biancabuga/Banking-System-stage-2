package org.poo.main;

public final class NumberOfTransactions implements CashbackStrategy {

    // Constante pentru numărul tranzacțiilor necesare pentru cashback
    private static final int TRANSACTIONS_FOOD_THRESHOLD = 3;
    private static final int TRANSACTIONS_CLOTHES_THRESHOLD = 6;
    private static final int TRANSACTIONS_TECH_THRESHOLD = 11;

    // Constante pentru procentele de cashback
    private static final double CASHBACK_FOOD = 0.02;
    private static final double CASHBACK_CLOTHES = 0.05;
    private static final double CASHBACK_TECH = 0.10;

    @Override
    public double calculateCashback(final double amount, final Account account) {
        double cashbackPercentage = 0.0;
        int cashBackDone = 0;

        if (account.getTransactionsFood()
                ==
                TRANSACTIONS_FOOD_THRESHOLD && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = CASHBACK_FOOD;
        } else if (account.getTransactionsClothes()
                ==
                TRANSACTIONS_CLOTHES_THRESHOLD && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = CASHBACK_CLOTHES;
        } else if (account.getTransactionsTech()
                ==
                TRANSACTIONS_TECH_THRESHOLD && cashBackDone == 0) {
            cashBackDone = 1;
            cashbackPercentage = CASHBACK_TECH;
        }

        // Returnăm cashback-ul calculat
        return cashbackPercentage * amount;
    }
}
