package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Comparator;
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

    /**
     * @return tooPoorToSplit
     */
    public String getTooPoorToSplit() {
        return tooPoorToSplit;
    }

    /**
     * setter
     * @param tooPoorToSplit
     */
    public void setTooPoorToSplit(final String tooPoorToSplit) {
        this.tooPoorToSplit = tooPoorToSplit;
    }

    /**
     * getter
     * @return acceptance
     */
    public String getAcceptance() {
        return acceptance;
    }

    /**
     * setter
     * @param acceptance
     */
    public void setAcceptance(final String acceptance) {
        this.acceptance = acceptance;
    }

    /**
     * incrementeaza numarul de tranzactii de tip Clothes
     */
    public void incrClothes() {
        this.transactionsClothes++;
    }

    /**
     * incrementeaza tranzactiile de tip Food
     */
    public void incrFood() {
        this.transactionsFood++;
    }

    /**
     * incrementeaza tranzactiile de tip Tech
     */
    public void incrTech() {
        this.transactionsTech++;
    }

    /**
     * getter
     */
    public double getTransactionsClothes() {
        return transactionsClothes;
    }

    /**
     * @param transactionsClothes
     */
    public void setTransactionsClothes(
            final double transactionsClothes) {
        this.transactionsClothes = transactionsClothes;
    }

    /**
     * @return transactionsFood
     */
    public double getTransactionsFood() {
        return transactionsFood;
    }

    /**
     * @param transactionsFood
     */
    public void setTransactionsFood(
            final double transactionsFood) {
        this.transactionsFood = transactionsFood;
    }

    /**
     * @return transactionsTech
     */
    public double getTransactionsTech() {
        return transactionsTech;
    }

    /**
     * @param transactionsTech
     */
    public void setTransactionsTech(final double transactionsTech) {
        this.transactionsTech = transactionsTech;
    }

    /**
     * @param amount
     */
    public void addToTotalSpent(final double amount) {
        this.totalSpent += amount;
    }

    /**
     * @return totalSpent
     */
    public double getTotalSpent() {
        return totalSpent;
    }

    /**
     * @param totalSpent
     */
    public void setTotalSpent(final double totalSpent) {
        this.totalSpent = totalSpent;
    }

    /**
     * @return plan
     */
    public String getPlan() {
        return plan;
    }

    /**
     * @param plan
     */
    public void setPlan(final String plan) {
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

    /**
     * @return iban
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * @param ibanul
     */
    public void setIBAN(final String ibanul) {
        this.iban = ibanul;
    }

    /**
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @param cards
     */
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

    /**
     * @return transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions
     */
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

    /**
     * @return interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * @param interestRate
     */
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @return minBalance
     */
    public double getMinBalance() {
        return minBalance;
    }

    /**
     * @param minBalance
     */
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

}
