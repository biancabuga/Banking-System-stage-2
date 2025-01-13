package org.poo.main;

import java.util.List;

public class SavingsAccount extends Account{
    public SavingsAccount(String iban, double balance, String currency, String type, List<Card> cards) {
        super(iban, balance, currency, "savings", cards);
    }
}
