package org.poo.main;

import java.util.List;

public class BusinessAccount extends Account {
    public BusinessAccount(final String iban, final double balance,
                           final String currency, final String type,
                           final List<Card> cards) {
        super(iban, balance, currency, "business", cards);
    }
}
