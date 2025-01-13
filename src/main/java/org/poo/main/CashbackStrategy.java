package org.poo.main;

import org.poo.fileio.ExchangeInput;
import java.util.List;

public interface CashbackStrategy {
    double calculateCashback(double amount, Account account);
}
