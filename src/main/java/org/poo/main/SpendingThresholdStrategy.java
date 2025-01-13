package org.poo.main;

import org.poo.fileio.ExchangeInput;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class SpendingThresholdStrategy implements CashbackStrategy{

    @Override
    public double calculateCashback(double amount, Account account) {
        double totalSpentInRON = account.getTotalSpent();
        double cashbackPercentage = 0.0;

        if (totalSpentInRON >= 500) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = 0.25;
                case "silver" -> cashbackPercentage = 0.5;
                case "gold" -> cashbackPercentage = 0.7;
            }
        } else if (totalSpentInRON >= 300 && totalSpentInRON < 500) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = 0.2;
                case "silver" -> cashbackPercentage = 0.4;
                case "gold" -> cashbackPercentage = 0.55;
            }
        } else if (totalSpentInRON >= 100 && totalSpentInRON < 300) {
            switch (account.getPlan()) {
                case "standard", "student" -> cashbackPercentage = 0.1;
                case "silver" -> cashbackPercentage = 0.3;
                case "gold" -> cashbackPercentage = 0.5;
            }
        }

        return (amount * cashbackPercentage) / 100;
    }
}
