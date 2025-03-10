package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class User implements JsonOutput {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;
    private int timestamp;
    private ArrayList<Transaction> transactions;
    private String birthDate;
    private String occupation;
    private String plan;
    private String accept;

    public String getAccept() {
        return accept;
    }

    public void setAccept(final String accept) {
        this.accept = accept;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(final String plan) {
        this.plan = plan;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final String theBirthDate) {
        this.birthDate = theBirthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(final String occupation) {
        this.occupation = occupation;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(final ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    // Constructor privat pentru a preveni instanțierea directă
    private User(final UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.accounts = builder.accounts;
        this.birthDate = builder.birthDate;
        this.occupation = builder.occupation;
        this.plan = builder.plan;
    }

    // Getteri și Setteri
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();
        userNode.put("firstName", firstName);
        userNode.put("lastName", lastName);
        userNode.put("email", email);

        ArrayNode accountsNode = mapper.createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.toJson());
        }
        userNode.set("accounts", accountsNode);
        return userNode;
    }

    /**
     * adaugarea unui cont la utilizator
     * @param account
     */
    public void addAccount(final Account account) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        accounts.add(account);
    }

    /**
     * adaugam tranzactiile user-ului
     * @param transaction
     */
    public void addTransaction(final Transaction transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }

    // Clasa Builder internă statică
    public static class UserBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private int timestamp;
        private ArrayList<Account> accounts = new ArrayList<>();
        private ArrayList<Transaction> transactions = new ArrayList<>();
        private String birthDate;
        private String occupation;
        private String plan;

        /**
         * Setează planul utilizatorului și returnează instanța builder-ului.
         *
         * @param thePlan Planul asociat utilizatorului (ex. standard, student, silver).
         * @return Instanța curentă a builder-ului.
         */
        public final UserBuilder plan(final String thePlan) {
            this.plan = thePlan;
            return this;
        }

        /**
         * Setează data de naștere a utilizatorului și returnează instanța builder-ului.
         *
         * @param theBirthDate Data de naștere a
         *                    utilizatorului în formatul dorit (ex. "yyyy-MM-dd").
         * @return Instanța curentă a builder-ului.
         */
        public final UserBuilder birthDate(final String theBirthDate) {
            this.birthDate = theBirthDate;
            return this;
        }

        /**
         * Setează ocupația utilizatorului și returnează instanța builder-ului.
         *
         * @param theOccupation Ocupația utilizatorului (ex. programator, student).
         * @return Instanța curentă a builder-ului.
         */
        public final UserBuilder occupation(final String theOccupation) {
            this.occupation = theOccupation;
            return this;
        }

        /**
         * Setează lista tranzacțiilor asociate utilizatorului și returnează instanța builder-ului.
         *
         * @param myTransactions Lista de tranzacții a utilizatorului.
         * @return Instanța curentă a builder-ului.
         */
        private UserBuilder transactions(final ArrayList<Transaction> myTransactions) {
            this.transactions = myTransactions;
            return this;
        }

        /**
         * metoda pentru setarea fiecarui camp
         * @param theFirstName
         * @return
         */
        public final UserBuilder firstName(final String theFirstName) {
            this.firstName = theFirstName;
            return this;
        }

        /**
         * metoda pentru setarea fiecarui camp
         * @param theLastName
         * @return
         */
        public final UserBuilder lastName(final String theLastName) {
            this.lastName = theLastName;
            return this;
        }

        /**
         * metoda pentru setarea fiecarui camp
         * @param theEmail
         * @return
         */
        public final UserBuilder email(final String theEmail) {
            this.email = theEmail;
            return this;
        }

        /**
         * metoda pentru setarea fiecarui camp
         * @param theAccounts
         * @return
         */
        public final UserBuilder accounts(final ArrayList<Account> theAccounts) {
            this.accounts = theAccounts;
            return this;
        }


        /**
         * metoda pentru setarea fiecarui camp
         * @param theTimestamp
         * @return
         */
        public final UserBuilder timestamp(final int theTimestamp) {
            this.timestamp = theTimestamp;
            return this;
        }

        /**
         * metoda pentru a construi obicetul User
         * @return
         */
        public final User build() {
            return new User(this);
        }
    }
}
