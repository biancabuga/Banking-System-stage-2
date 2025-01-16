package org.poo.main;

import java.util.List;

public class SavingsAccount extends Account {
    public SavingsAccount(final String iban, final double balance,
                          final String currency, final String type,
                          final List<Card> cards) {
        super(iban, balance, currency, "savings", cards);
    }
}
