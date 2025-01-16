package org.poo.main;

public interface CashbackStrategy {
    /**
     * calculeaza cashback
     * @param amount
     * @param account
     * @return
     */
    double calculateCashback(double amount, Account account);
}
