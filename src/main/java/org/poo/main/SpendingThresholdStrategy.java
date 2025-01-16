package org.poo.main;

public final class SpendingThresholdStrategy implements CashbackStrategy {

    /**
     * Pragul pentru cheltuieli mari (în RON).
     * Dacă totalul cheltuit depășește această valoare, se aplică cashback-ul corespunzător.
     */
    private static final double THRESHOLD_HIGH = 500.0;

    /**
     * Pragul pentru cheltuieli medii (în RON).
     * Dacă totalul cheltuit se află între această valoare și pragul mare,
     * se aplică cashback-ul corespunzător.
     */
    private static final double THRESHOLD_MEDIUM = 300.0;

    /**
     * Pragul pentru cheltuieli mici (în RON).
     * Dacă totalul cheltuit se află între această valoare și pragul mediu,
     * se aplică cashback-ul corespunzător.
     */
    private static final double THRESHOLD_LOW = 100.0;

    /**
     * Procentul de cashback pentru utilizatorii cu plan standard sau student
     * care depășesc pragul mare.
     */
    private static final double CASHBACK_STANDARD_HIGH = 0.25;

    /**
     * Procentul de cashback pentru utilizatorii cu plan silver
     * care depășesc pragul mare.
     */
    private static final double CASHBACK_SILVER_HIGH = 0.5;

    /**
     * Procentul de cashback pentru utilizatorii cu plan gold
     * care depășesc pragul mare.
     */
    private static final double CASHBACK_GOLD_HIGH = 0.7;

    /**
     * Procentul de cashback pentru utilizatorii cu plan standard sau student
     * care se află în intervalul pragului mediu.
     */
    private static final double CASHBACK_STANDARD_MEDIUM = 0.2;

    /**
     * Procentul de cashback pentru utilizatorii cu plan silver
     * care se află în intervalul pragului mediu.
     */
    private static final double CASHBACK_SILVER_MEDIUM = 0.4;

    /**
     * Procentul de cashback pentru utilizatorii cu plan gold
     * care se află în intervalul pragului mediu.
     */
    private static final double CASHBACK_GOLD_MEDIUM = 0.55;

    /**
     * Procentul de cashback pentru utilizatorii cu plan standard sau student
     * care se află în intervalul pragului mic.
     */
    private static final double CASHBACK_STANDARD_LOW = 0.1;

    /**
     * Procentul de cashback pentru utilizatorii cu plan silver
     * care se află în intervalul pragului mic.
     */
    private static final double CASHBACK_SILVER_LOW = 0.3;

    /**
     * Procentul de cashback pentru utilizatorii cu plan gold
     * care se află în intervalul pragului mic.
     */
    private static final double CASHBACK_GOLD_LOW = 0.5;

    private static final double PERCENT_HIGH = 100.0;

    @Override
    public double calculateCashback(final double amount, final Account account) {
        double totalSpentInRON = account.getTotalSpent();
        double cashbackPercentage = 0.0;

        if (totalSpentInRON >= THRESHOLD_HIGH) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = CASHBACK_STANDARD_HIGH;
                case "silver" -> cashbackPercentage = CASHBACK_SILVER_HIGH;
                case "gold" -> cashbackPercentage = CASHBACK_GOLD_HIGH;
                default -> cashbackPercentage = 0;
            }
        } else if (totalSpentInRON >= THRESHOLD_MEDIUM) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = CASHBACK_STANDARD_MEDIUM;
                case "silver" -> cashbackPercentage = CASHBACK_SILVER_MEDIUM;
                case "gold" -> cashbackPercentage = CASHBACK_GOLD_MEDIUM;
                default -> cashbackPercentage = 0;
            }
        } else if (totalSpentInRON >= THRESHOLD_LOW) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = CASHBACK_STANDARD_LOW;
                case "silver" -> cashbackPercentage = CASHBACK_SILVER_LOW;
                case "gold" -> cashbackPercentage = CASHBACK_GOLD_LOW;
                default -> cashbackPercentage = 0;
            }
        }

        return (amount * cashbackPercentage) / PERCENT_HIGH;
    }
}
