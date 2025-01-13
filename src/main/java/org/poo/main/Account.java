package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String iban;
    private double balance;
    private String currency;
    private String type;
    private List<Card> cards;
    private double interestRate;
    private List<Transaction> transactions = new ArrayList<>();
    private double minBalance;
    private String alias;
    private String plan;
    private double totalSpent;
    private double transactionsClothes;
    private double transactionsFood;
    private double transactionsTech;
    private String acceptance;
    private String tooPoorToSplit;
    private String acceptanceEqual;

    public String getAcceptanceEqual() {
        return acceptanceEqual;
    }

    public void setAcceptanceEqual(String acceptanceEqual) {
        this.acceptanceEqual = acceptanceEqual;
    }

    public String getTooPoorToSplit() {
        return tooPoorToSplit;
    }

    public void setTooPoorToSplit(String tooPoorToSplit) {
        this.tooPoorToSplit = tooPoorToSplit;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public void incrClothes() {
        this.transactionsClothes++;
    }

    public void incrFood() {
        this.transactionsFood++;
    }

    public void incrTech() {
        this.transactionsTech++;
    }

    public double getTransactionsClothes() {
        return transactionsClothes;
    }

    public void setTransactionsClothes(double transactionsClothes) {
        this.transactionsClothes = transactionsClothes;
    }

    public double getTransactionsFood() {
        return transactionsFood;
    }

    public void setTransactionsFood(double transactionsFood) {
        this.transactionsFood = transactionsFood;
    }

    public double getTransactionsTech() {
        return transactionsTech;
    }

    public void setTransactionsTech(double transactionsTech) {
        this.transactionsTech = transactionsTech;
    }

    public void addToTotalSpent(double amount) {
        this.totalSpent += amount;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }


    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Account(final String iban, final double balance,
                   final String currency, final String type,
                   final List<Card> cards) {
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
        this.type = type;
        this.cards = cards;
    }

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(final String ibanul) {
        this.iban = ibanul;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(final double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(final List<Card> cards) {
        this.cards = cards;
    }

    /**
     * metoda pentru afisare
     * @return
     */

    public void sortTransactionsByTimestamp() {
        transactions.sort(Comparator.comparingInt(Transaction::getTimestamp));
    }

    /**
     * metoda pentru afisare
     * @return
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode accountNode = mapper.createObjectNode();
        accountNode.put("IBAN", iban);
        accountNode.put("balance", balance);
        accountNode.put("currency", currency);
        accountNode.put("type", type);

        ArrayNode cardsNode = mapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.toJson());
        }
        accountNode.set("cards", cardsNode);
        return accountNode;
    }

    /**
     * metoda pentru a adauga un card la un cont
     * @param card
     */
    public void addCard(final Card card) {
        if (cards == null) {
            cards = new ArrayList<>();
        }
        cards.add(card);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(final List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * metoda pentru a adauga o tranzactie la tranzactiile contului
     * @param transaction
     */
    public void addTransaction(final Transaction transaction) {
        if (transaction == null) {
            transactions.add(transaction);
        }
        transactions.add(transaction);
    }


    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

}
