package org.poo.main;

import java.util.List;

public final class AccountFactory {

    private AccountFactory() {
        // Constructor privat pentru a preveni instanțierea
    }

    /**
     *
     * @param iban
     * @param balance
     * @param currency
     * @param type
     * @param cards
     * @return
     */
    public static Account createAccount(final String iban,
                                        final double balance, final String currency,
                                        final String type, final List<Card> cards) {
        /**
         * cream conturile in functie de ce tip sunt
         */
        switch (type.toLowerCase()) {
            case "savings":
                return new SavingsAccount(iban, balance, currency, "savings", cards);
            case "classic":
                return new ClassicAccount(iban, balance, currency, "classic", cards);
            case "business":
                return new BusinessAccount(iban, balance, currency, "business", cards);
            default:
                return null;
        }
    }
}
