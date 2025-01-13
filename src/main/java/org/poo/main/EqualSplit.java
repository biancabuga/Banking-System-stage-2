package org.poo.main;

import java.util.List;

public class EqualSplit {
    public String command;
    public String splitPaymentType;
    public List<String> accounts;
    public double amount;
    public List<Double> amountForUsers;
    public String currency;
    public int timestamp;
    public int numberOfAccounts;
    public int numberOfAccountsCommand;
    public List<User> users;
    public List<Double> aux;
    public int numberOfAccountsInvolved;
    public String error;
    public int numberOfPoorAccounts;
    public String firstAccount;

    public EqualSplit() {

    }

    public String getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(String firstAccount) {
        this.firstAccount = firstAccount;
    }

    public int getNumberOfPoorAccounts() {
        return numberOfPoorAccounts;
    }

    public void setNumberOfPoorAccounts(int numberOfPoorAccounts) {
        this.numberOfPoorAccounts = numberOfPoorAccounts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getNumberOfAccountsInvolved() {
        return numberOfAccountsInvolved;
    }

    public void setNumberOfAccountsInvolved(int numberOfAccountsInvolved) {
        this.numberOfAccountsInvolved = numberOfAccountsInvolved;
    }

    public List<Double> getAux() {
        return aux;
    }

    public void setAux(List<Double> aux) {
        this.aux = aux;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getNumberOfAccountsCommand() {
        return numberOfAccountsCommand;
    }

    public void setNumberOfAccountsCommand(int numberOfAccountsCommand) {
        this.numberOfAccountsCommand = numberOfAccountsCommand;
    }

    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(int numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSplitPaymentType() {
        return splitPaymentType;
    }

    public void setSplitPaymentType(String splitPaymentType) {
        this.splitPaymentType = splitPaymentType;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Double> getAmountForUsers() {
        return amountForUsers;
    }

    public void setAmountForUsers(List<Double> amountForUsers) {
        this.amountForUsers = amountForUsers;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
