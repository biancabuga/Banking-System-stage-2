package org.poo.main;

public final class CashbackContext {
    private CashbackStrategy strategy;

    /**
     * @param strategy
     */
    public void setStrategy(
            final CashbackStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @param amount
     * @param account
     * @return cashback
     */
    public double executeStrategy(
            final double amount, final Account account) {
        return strategy.calculateCashback(amount, account);
    }
}
