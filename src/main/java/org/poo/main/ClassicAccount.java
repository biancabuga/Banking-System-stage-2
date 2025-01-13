package org.poo.main;

import java.util.List;

public class ClassicAccount extends Account {
    public ClassicAccount(String iban, double balance, String currency, String type, List<Card> cards) {
        super(iban, balance, currency, "classic", cards);
    }
}
