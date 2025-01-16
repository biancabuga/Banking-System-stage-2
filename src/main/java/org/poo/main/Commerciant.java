package org.poo.main;

public class Commerciant {
    private String commerciant;
    private int id;
    private String account;
    private String type;
    private String cashbackStrategy;

    /**
     * Obține numele comerciantului.
     *
     * @return Numele comerciantului.
     */
    public String getCommerciant() {
        return commerciant;
    }

    /**
     * Setează numele comerciantului.
     *
     * @param commerciant Numele comerciantului.
     */
    public void setCommerciant(final String commerciant) {
        this.commerciant = commerciant;
    }

    /**
     * Obține ID-ul comerciantului.
     *
     * @return ID-ul comerciantului.
     */
    public int getId() {
        return id;
    }

    /**
     * Setează ID-ul comerciantului.
     *
     * @param id ID-ul comerciantului.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Obține contul asociat comerciantului.
     *
     * @return Contul comerciantului.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setează contul asociat comerciantului.
     *
     * @param account Contul comerciantului.
     */
    public void setAccount(final String account) {
        this.account = account;
    }

    /**
     * Obține tipul comerciantului.
     *
     * @return Tipul comerciantului.
     */
    public String getType() {
        return type;
    }

    /**
     * Setează tipul comerciantului.
     *
     * @param type Tipul comerciantului.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Obține strategia de cashback a comerciantului.
     *
     * @return Strategia de cashback.
     */
    public String getCashbackStrategy() {
        return cashbackStrategy;
    }

    /**
     * Setează strategia de cashback a comerciantului.
     *
     * @param cashbackStrategy Strategia de cashback.
     */
    public void setCashbackStrategy(final String cashbackStrategy) {
        this.cashbackStrategy = cashbackStrategy;
    }

    /**
     * Constructor pentru crearea unui comerciant cu informațiile specificate.
     *
     * @param commerciant       Numele comerciantului.
     * @param id                ID-ul comerciantului.
     * @param account           Contul asociat comerciantului.
     * @param type              Tipul comerciantului.
     * @param cashbackStrategy  Strategia de cashback a comerciantului.
     */
    public Commerciant(final String commerciant, final int id,
                       final String account, final String type,
                       final String cashbackStrategy) {
        this.commerciant = commerciant;
        this.id = id;
        this.account = account;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
    }
}
