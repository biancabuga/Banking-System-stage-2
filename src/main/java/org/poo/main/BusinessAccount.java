package org.poo.main;

import java.util.List;

public class BusinessAccount extends Account {
    public BusinessAccount(String iban, double balance, String currency, String type, List<Card> cards) {
        super(iban, balance, currency, "business", cards);
    }
}
