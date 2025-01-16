package org.poo.main;

import java.util.List;

public class SplitPayment {
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

    public SplitPayment() {

    }

    /**
     * Primul cont implicat în plata împărțită.
     * @return Primul cont.
     */
    public String getFirstAccount() {
        return firstAccount;
    }

    /**
     * Setează primul cont implicat în plata împărțită.
     * @param firstAccount Primul cont.
     */
    public void setFirstAccount(final String firstAccount) {
        this.firstAccount = firstAccount;
    }

    /**
     * Obține numărul de conturi cu resurse insuficiente implicate în plata împărțită.
     * @return Numărul de conturi cu resurse insuficiente.
     */
    public int getNumberOfPoorAccounts() {
        return numberOfPoorAccounts;
    }

    /**
     * Setează numărul de conturi cu resurse insuficiente implicate în plata împărțită.
     * @param numberOfPoorAccounts Numărul de conturi cu resurse insuficiente.
     */
    public void setNumberOfPoorAccounts(final int numberOfPoorAccounts) {
        this.numberOfPoorAccounts = numberOfPoorAccounts;
    }

    /**
     * Obține mesajul de eroare asociat plății împărțite.
     * @return Mesajul de eroare.
     */
    public String getError() {
        return error;
    }

    /**
     * Setează mesajul de eroare asociat plății împărțite.
     * @param error Mesajul de eroare.
     */
    public void setError(final String error) {
        this.error = error;
    }

    /**
     * Obține numărul de conturi direct implicate în plata împărțită.
     * @return Numărul de conturi implicate.
     */
    public int getNumberOfAccountsInvolved() {
        return numberOfAccountsInvolved;
    }

    /**
     * Setează numărul de conturi direct implicate în plata împărțită.
     * @param numberOfAccountsInvolved Numărul de conturi implicate.
     */
    public void setNumberOfAccountsInvolved(final int numberOfAccountsInvolved) {
        this.numberOfAccountsInvolved = numberOfAccountsInvolved;
    }

    /**
     * Obține lista auxiliară de numere de tip double.
     * @return Lista auxiliară.
     */
    public List<Double> getAux() {
        return aux;
    }

    /**
     * Setează lista auxiliară de numere de tip double.
     * @param aux Lista auxiliară.
     */
    public void setAux(final List<Double> aux) {
        this.aux = aux;
    }

    /**
     * Obține lista utilizatorilor implicați în plata împărțită.
     * @return Lista utilizatorilor.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Setează lista utilizatorilor implicați în plata împărțită.
     * @param users Lista utilizatorilor.
     */
    public void setUsers(final List<User> users) {
        this.users = users;
    }

    /**
     * Obține numărul de conturi specificate în comandă.
     * @return Numărul de conturi specificate.
     */
    public int getNumberOfAccountsCommand() {
        return numberOfAccountsCommand;
    }

    /**
     * Setează numărul de conturi specificate în comandă.
     * @param numberOfAccountsCommand Numărul de conturi specificate.
     */
    public void setNumberOfAccountsCommand(final int numberOfAccountsCommand) {
        this.numberOfAccountsCommand = numberOfAccountsCommand;
    }

    /**
     * Obține numărul total de conturi implicate în plata împărțită.
     * @return Numărul total de conturi implicate.
     */
    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    /**
     * Setează numărul total de conturi implicate în plata împărțită.
     * @param numberOfAccounts Numărul total de conturi implicate.
     */
    public void setNumberOfAccounts(final int numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    /**
     * Obține timestamp-ul plății împărțite.
     * @return Timestamp-ul plății împărțite.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Setează timestamp-ul plății împărțite.
     * @param timestamp Timestamp-ul plății împărțite.
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Obține comanda asociată plății împărțite.
     * @return Comanda plății împărțite.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Setează comanda asociată plății împărțite.
     * @param command Comanda plății împărțite.
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     * Obține tipul plății împărțite.
     * @return Tipul plății împărțite.
     */
    public String getSplitPaymentType() {
        return splitPaymentType;
    }

    /**
     * Setează tipul plății împărțite.
     * @param splitPaymentType Tipul plății împărțite.
     */
    public void setSplitPaymentType(final String splitPaymentType) {
        this.splitPaymentType = splitPaymentType;
    }

    /**
     * Obține lista conturilor implicate în plata împărțită.
     * @return Lista conturilor implicate.
     */
    public List<String> getAccounts() {
        return accounts;
    }

    /**
     * Setează lista conturilor implicate în plata împărțită.
     * @param accounts Lista conturilor implicate.
     */
    public void setAccounts(final List<String> accounts) {
        this.accounts = accounts;
    }

    /**
     * Obține suma totală a plății împărțite.
     * @return Suma totală.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setează suma totală a plății împărțite.
     * @param amount Suma totală.
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Obține lista sumelor pentru fiecare utilizator implicat.
     * @return Lista sumelor pentru utilizatori.
     */
    public List<Double> getAmountForUsers() {
        return amountForUsers;
    }

    /**
     * Setează lista sumelor pentru fiecare utilizator implicat.
     * @param amountForUsers Lista sumelor pentru utilizatori.
     */
    public void setAmountForUsers(final List<Double> amountForUsers) {
        this.amountForUsers = amountForUsers;
    }

    /**
     * Obține moneda utilizată pentru plata împărțită.
     * @return Moneda utilizată.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setează moneda utilizată pentru plata împărțită.
     * @param currency Moneda utilizată.
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Construiește un obiect SplitPayment cu atributele specificate.
     * @param command Comanda plății împărțite.
     * @param splitPaymentType Tipul plății împărțite.
     * @param accounts Lista conturilor implicate.
     * @param amount Suma totală.
     * @param amountForUsers Lista sumelor pentru fiecare utilizator.
     * @param currency Moneda utilizată.
     * @param timestamp Timestamp-ul plății.
     */
    public SplitPayment(final String command, final String splitPaymentType,
                        final List<String> accounts, final double amount,
                        final List<Double> amountForUsers, final String currency,
                        final int timestamp) {
        this.command = command;
        this.splitPaymentType = splitPaymentType;
        this.accounts = accounts;
        this.amount = amount;
        this.amountForUsers = amountForUsers;
        this.currency = currency;
        this.timestamp = timestamp;
    }
}
