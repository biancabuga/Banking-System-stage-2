package org.poo.main;

import org.poo.fileio.ExchangeInput;
import java.util.List;

public class CashbackContext {
    private CashbackStrategy strategy;

    public void setStrategy(CashbackStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(double amount, Account account) {
        return strategy.calculateCashback(amount, account);
    }
}
