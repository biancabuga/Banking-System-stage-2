package org.poo.main;

import java.util.List;

public class ClassicAccount extends Account {
    public ClassicAccount(final String iban, final double balance,
                          final String currency, final String type,
                          final List<Card> cards) {
        super(iban, balance, currency, "classic", cards);
    }
}
