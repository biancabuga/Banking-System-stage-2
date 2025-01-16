package org.poo.main;

import java.util.List;

public class EqualSplit {
    private String command;
    private String splitPaymentType;
    private List<String> accounts;
    private double amount;
    private List<Double> amountForUsers;
    private String currency;
    private int timestamp;
    private int numberOfAccounts;
    private int numberOfAccountsCommand;
    private List<User> users;
    private List<Double> aux;
    private int numberOfAccountsInvolved;
    private String error;
    private int numberOfPoorAccounts;
    private String firstAccount;

    /**
     * Constructor implicit pentru clasa EqualSplit.
     */
    public EqualSplit() {

    }

    /**
     * Returneaza primul cont implicat in tranzactie.
     * @return primul cont
     */
    public String getFirstAccount() {

        return firstAccount;
    }

    /**
     * Seteaza primul cont implicat in tranzactie.
     * @param firstAccount primul cont
     */
    public void setFirstAccount(final String firstAccount) {

        this.firstAccount = firstAccount;
    }

    /**
     * Returneaza numarul de conturi cu fonduri insuficiente.
     * @return numarul de conturi cu fonduri insuficiente
     */
    public int getNumberOfPoorAccounts() {

        return numberOfPoorAccounts;
    }

    /**
     * Seteaza numarul de conturi cu fonduri insuficiente.
     * @param numberOfPoorAccounts numarul de conturi cu fonduri insuficiente
     */
    public void setNumberOfPoorAccounts(
            final int numberOfPoorAccounts) {

        this.numberOfPoorAccounts = numberOfPoorAccounts;
    }

    /**
     * Returneaza mesajul de eroare.
     * @return mesajul de eroare
     */
    public String getError() {

        return error;
    }

    /**
     * Seteaza mesajul de eroare.
     * @param error mesajul de eroare
     */
    public void setError(final String error) {

        this.error = error;
    }

    /**
     * Returneaza numarul efectiv de conturi implicate in tranzactie.
     * @return numarul de conturi implicate
     */
    public int getNumberOfAccountsInvolved() {

        return numberOfAccountsInvolved;
    }

    /**
     * Seteaza numarul efectiv de conturi implicate in tranzactie.
     * @param numberOfAccountsInvolved numarul de conturi implicate
     */
    public void setNumberOfAccountsInvolved(
            final int numberOfAccountsInvolved) {
        this.numberOfAccountsInvolved = numberOfAccountsInvolved;
    }

    /**
     * Returneaza lista auxiliara pentru calcule suplimentare.
     * @return lista auxiliara
     */
    public List<Double> getAux() {

        return aux;
    }

    /**
     * Seteaza lista auxiliara pentru calcule suplimentare.
     * @param aux lista auxiliara
     */
    public void setAux(final List<Double> aux) {

        this.aux = aux;
    }

    /**
     * Returneaza lista utilizatorilor implicati.
     * @return lista utilizatorilor
     */
    public List<User> getUsers() {

        return users;
    }

    /**
     * Seteaza lista utilizatorilor implicati.
     * @param users lista utilizatorilor
     */
    public void setUsers(final List<User> users) {

        this.users = users;
    }

    /**
     * Returneaza numarul de conturi specificate in comanda.
     * @return numarul de conturi specificate
     */
    public int getNumberOfAccountsCommand() {

        return numberOfAccountsCommand;
    }

    /**
     * Seteaza numarul de conturi specificate in comanda.
     * @param numberOfAccountsCommand numarul de conturi specificate
     */
    public void setNumberOfAccountsCommand(
            final int numberOfAccountsCommand) {
        this.numberOfAccountsCommand = numberOfAccountsCommand;
    }

    /**
     * Returneaza numarul total de conturi implicate.
     * @return numarul total de conturi
     */
    public int getNumberOfAccounts() {

        return numberOfAccounts;
    }

    /**
     * Seteaza numarul total de conturi implicate.
     * @param numberOfAccounts numarul total de conturi
     */
    public void setNumberOfAccounts(
            final int numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    /**
     * Returneaza marcajul temporal al tranzactiei.
     * @return marcajul temporal
     */
    public int getTimestamp() {

        return timestamp;
    }

    /**
     * Seteaza marcajul temporal al tranzactiei.
     * @param timestamp marcajul temporal
     */
    public void setTimestamp(final int timestamp) {

        this.timestamp = timestamp;
    }

    /**
     * Returneaza comanda asociata tranzactiei.
     * @return comanda
     */
    public String getCommand() {

        return command;
    }

    /**
     * Seteaza comanda asociata tranzactiei.
     * @param command comanda
     */
    public void setCommand(final String command) {

        this.command = command;
    }

    /**
     * Returneaza tipul de impartire a platii.
     * @return tipul de impartire
     */
    public String getSplitPaymentType() {

        return splitPaymentType;
    }

    /**
     * Seteaza tipul de impartire a platii.
     * @param splitPaymentType tipul de impartire
     */
    public void setSplitPaymentType(
            final String splitPaymentType) {

        this.splitPaymentType = splitPaymentType;
    }

    /**
     * Returneaza lista conturilor implicate in plata.
     * @return lista conturilor
     */
    public List<String> getAccounts() {

        return accounts;
    }

    /**
     * Seteaza lista conturilor implicate in plata.
     * @param accounts lista conturilor
     */
    public void setAccounts(final List<String> accounts) {

        this.accounts = accounts;
    }

    /**
     * Returneaza suma totala de impartit.
     * @return suma totala
     */
    public double getAmount() {

        return amount;
    }

    /**
     * Seteaza suma totala de impartit.
     * @param amount suma totala
     */
    public void setAmount(final double amount) {

        this.amount = amount;
    }

    /**
     * Returneaza sumele individuale pentru fiecare utilizator.
     * @return sumele individuale
     */
    public List<Double> getAmountForUsers() {

        return amountForUsers;
    }

    /**
     * Seteaza sumele individuale pentru fiecare utilizator.
     * @param amountForUsers sumele individuale
     */
    public void setAmountForUsers(
            final List<Double> amountForUsers) {

        this.amountForUsers = amountForUsers;
    }

    /**
     * Returneaza moneda in care se efectueaza plata.
     * @return moneda
     */
    public String getCurrency() {

        return currency;
    }

    /**
     * Seteaza moneda in care se efectueaza plata.
     * @param currency moneda
     */
    public void setCurrency(final String currency) {

        this.currency = currency;
    }
}
