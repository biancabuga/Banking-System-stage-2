package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.*;
import org.poo.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private final static double MINIMUM_SILVER = 500;
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        var sortedFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .toList();

        for (File file : sortedFiles) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(CheckerConstants.TESTS_PATH + filePath1);
        ObjectInput inputData = objectMapper.readValue(file, ObjectInput.class);

        ArrayNode output = objectMapper.createArrayNode();

        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */
        Utils.resetRandom();
        List<User> users = new ArrayList<>();
        for (UserInput userInput : inputData.getUsers()) {
            users.add(new User.UserBuilder()
                    .firstName(userInput.getFirstName())
                    .lastName(userInput.getLastName())
                    .email(userInput.getEmail())
                    .accounts(new ArrayList<>())
                    .birthDate(userInput.getBirthDate())
                    .occupation(userInput.getOccupation())
                    .build()
            );
        }

        SplitPayment splitPayment = new SplitPayment();
        EqualSplit equalSplit = new EqualSplit();

        Map<String, Double> exchangeRates = new HashMap<>();
        if (inputData.getExchangeRates() != null) {
            for (ExchangeInput exchange : inputData.getExchangeRates()) {
                exchangeRates.put(exchange.getFrom() + "_" + exchange.getTo(), exchange.getRate());
            }
        }

        List<Commerciant> commerciants = new ArrayList<>();
        if (inputData.getCommerciants() != null) {
            for (CommerciantInput commerciantInput : inputData.getCommerciants()) {
                Commerciant commerciant = new Commerciant(
                        commerciantInput.getCommerciant(),
                        commerciantInput.getId(),
                        commerciantInput.getAccount(),
                        commerciantInput.getType(),
                        commerciantInput.getCashbackStrategy()
                );
                commerciants.add(commerciant);
            }
        }


        for (CommandInput command : inputData.getCommands()) {
            switch (command.getCommand()) {
                case "printUsers" -> {
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", "printUsers");
                    ArrayNode usersOutput = objectMapper.createArrayNode();
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            for (Card card : account.getCards()) {
                                if (card.getIsBlocked() == 1) {
                                    card.setStatus("frozen");
                                } else {
                                    card.setStatus("active");
                                }
                            }
                        }
                        usersOutput.add(user.toJson());
                    }
                    commandOutput.set("output", usersOutput);
                    commandOutput.put("timestamp", command.getTimestamp());
                    output.add(commandOutput);
                }
                case "printTransactions" -> {
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", "printTransactions");
                    ArrayNode transactionsOutput = objectMapper.createArrayNode();
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            user.getTransactions().sort(
                                    Comparator.comparing(Transaction::getTimestamp));
                            for (Transaction transaction : user.getTransactions()) {
                                ObjectNode transactionNode = objectMapper.createObjectNode();
                                transactionNode.put("timestamp", transaction.getTimestamp());
                                transactionNode.put("description",
                                        transaction.getDescription());
                                if (transaction.getTransferType().equals("card_deletion")) {
                                    transactionNode.put("account", transaction.getAccount());
                                    transactionNode.put("card",
                                            transaction.getCardNumber());
                                    transactionNode.put("cardHolder",
                                            transaction.getCardHolder());
                                } else if (transaction.
                                        getTransferType().equals("card_creation")) {
                                    transactionNode.put("account",
                                            transaction.getAccount());
                                    transactionNode.put("card",
                                            transaction.getCardNumber());
                                    transactionNode.put("cardHolder",
                                            transaction.getCardHolder());
                                } else if (transaction.getTransferType().
                                        equals("online_payment")) {
                                    transactionNode.put("commerciant",
                                            transaction.getCommerciant());
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                } else if (transaction.getTransferType().
                                        equals("one_time_card_creation")) {
                                    transactionNode.put("account",
                                            transaction.getAccount());
                                    transactionNode.put("card",
                                            transaction.getCardNumber());
                                    transactionNode.put("cardHolder",
                                            transaction.getCardHolder());
                                } else if (transaction.getTransferType().
                                        equals("split_payment")) {
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                    transactionNode.put("currency",
                                            transaction.getCurrency());
                                    ArrayNode involvedAccounts = objectMapper.createArrayNode();
                                    for (String iban : transaction.getAccountsToSplit()) {
                                        involvedAccounts.add(iban);
                                    }
                                    transactionNode.set("involvedAccounts", involvedAccounts);
                                } else if (transaction.getTransferType().
                                        equals("changeInterestRate")) {
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                } else if (transaction.getTransferType().
                                        equals("split_payment_error")) {
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                    transactionNode.put("currency",
                                            transaction.getCurrency());
                                    transactionNode.put("error",
                                            "Account " + transaction.getError()
                                                    +
                                                    " has insufficient "
                                                    +
                                                    "funds for a split payment.");
                                    ArrayNode involvedAccounts = objectMapper.createArrayNode();
                                    for (String iban : transaction.getAccountsToSplit()) {
                                        involvedAccounts.add(iban);
                                    }
                                    transactionNode.set("involvedAccounts", involvedAccounts);
                                } else if (transaction.getTransferType().equals("lessThan21")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                } else if (transaction.getTransferType().equals("upgradeFee")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    transactionNode.put("accountIBAN",
                                            transaction.getReceiverIBAN());
                                    transactionNode.put("newPlanType",
                                            transaction.getPlan());
                                } else if (transaction.getTransferType().equals("cashWithdrawal")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                } else if (transaction.getTransferType().equals("noCash")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                } else if (transaction.getTransferType().equals("addInterest")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                    transactionNode.put("currency",
                                            transaction.getCommerciant());
                                } else if (transaction.getTransferType().
                                        equals("no classic account")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                } else if (transaction.getTransferType().equals("splitAccepted")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    if (transaction.getAmounts() != null) {
                                       ArrayNode sums = objectMapper.createArrayNode();
                                       for (Double amount : transaction.getAmounts()) {
                                           sums.add(amount);
                                       }
                                       transactionNode.set("amountForUsers", sums);
                                    }
                                    ArrayNode involvedAccounts = objectMapper.createArrayNode();
                                    for (String iban : transaction.getAccountsToSplit()) {
                                        involvedAccounts.add(iban);
                                    }
                                    transactionNode.set("involvedAccounts",
                                            involvedAccounts);
                                    transactionNode.put("currency",
                                            transaction.getCurrency());
                                    transactionNode.put("splitPaymentType",
                                            transaction.getTypeSplit());
                                } else if (transaction.getTransferType().equals("noMoneyToSplit")) {
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("timestamp",
                                            transaction.getTimestamp());
                                    transactionNode.put("error",
                                            transaction.getPoorAccount());
                                    if (transaction.getAmounts() != null) {
                                        ArrayNode sums = objectMapper.createArrayNode();
                                        for (Double amount : transaction.getAmounts()) {
                                            sums.add(amount);
                                        }
                                        transactionNode.set("amountForUsers", sums);
                                    } else {
                                        transactionNode.put("amount",
                                                transaction.getAmountToPay());
                                    }
                                    ArrayNode involvedAccounts = objectMapper.createArrayNode();
                                    for (String iban : transaction.getAccountsToSplit()) {
                                        involvedAccounts.add(iban);
                                    }
                                    transactionNode.set("involvedAccounts",
                                            involvedAccounts);
                                    transactionNode.put("currency",
                                            transaction.getCurrency());
                                    transactionNode.put("splitPaymentType",
                                            transaction.getTypeSplit());
                                } else if (transaction.getTransferType().equals("sent")
                                        ||
                                        transaction.getTransferType().equals("received")) {
                                    transactionNode.put("senderIBAN",
                                            transaction.getSenderIBAN());
                                    transactionNode.put("receiverIBAN",
                                            transaction.getReceiverIBAN());
                                    transactionNode.put("amount",
                                            transaction.getAmount());
                                    transactionNode.put("transferType",
                                            transaction.getTransferType());
                                }
                                transactionsOutput.add(transactionNode);
                            }

                        }
                    }
                    commandOutput.set("output", transactionsOutput);
                    commandOutput.put("timestamp",
                            command.getTimestamp());
                    output.add(commandOutput);
                }

                case "addAccount" -> {
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            String generatedIBAN;
                            generatedIBAN = Utils.generateIBAN();

                            Account newAccount = AccountFactory.createAccount(
                                    generatedIBAN,
                                    0.0,
                                    command.getCurrency(),
                                    command.getAccountType(),
                                    new ArrayList<>()
                            );

                            boolean silver = false;

                            for (Account account : user.getAccounts()) {
                                if (account.getPlan().equals("silver")) {
                                    silver = true;
                                }
                                if (silver) {
                                    break;
                                }
                            }

                            if (silver) {
                                newAccount.setPlan("silver");
                            } else {
                                if (user.getOccupation().equals("student")) {
                                    newAccount.setPlan("student");
                                } else {
                                    newAccount.setPlan("standard");
                                }
                            }

                            newAccount.setInterestRate(command.getInterestRate());

                            Transaction transaction = new Transaction(
                                    command.getTimestamp(),
                                    "New account created",
                                    "new_account"
                            );
                            user.addTransaction(transaction);
                            newAccount.addTransaction(transaction);
                            user.addAccount(newAccount);
                        }
                    }
                }
                case "addFunds" -> {
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                account.setBalance(account.getBalance()
                                        +
                                        command.getAmount());
                                if (user.getPlan() != null) {
                                    if (user.getPlan().equals("silver")
                                            &&
                                            command.getAmount() < MINIMUM_SILVER) {
                                        account.setBalance(account.getBalance()
                                                -
                                                command.getAmount() * 0.1);
                                    }
                                }
                            }
                        }
                    }
                }
                case "createCard" -> {
                    for (User user : users) {
                        String auxEmail = user.getEmail();
                        if (user.getEmail().equals(command.getEmail())) {
                            for (Account account : user.getAccounts()) {
                                if (account.getIBAN().equals(command.getAccount())) {
                                    // Generăm numărul cardului utilizând clasa Utils
                                    String generatedCardNumber = Utils.generateCardNumber();
                                    // Creăm cardul
                                    Card newCard = new Card(
                                            generatedCardNumber,
                                            "active", "classic" // Statusul cardului
                                    );
                                    // Adăugăm cardul în lista de carduri a contului
                                    account.addCard(newCard);
                                    Transaction transaction = new Transaction(
                                            command.getTimestamp(),
                                            "New card created",
                                            account.getIBAN(),
                                            generatedCardNumber,
                                            auxEmail,
                                            "card_creation", "1"
                                    );
                                    account.addTransaction(transaction);
                                    user.addTransaction(transaction);
                                }
                            }
                        }
                    }
                }
                case "createOneTimeCard" -> {
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            String auxEmail = user.getEmail();
                            for (Account account : user.getAccounts()) {
                                if (account.getIBAN().equals(command.getAccount())) {
                                    String generatedCardNumber = Utils.generateCardNumber();

                                    Card newCard = new Card(
                                            generatedCardNumber,
                                            "active", "one_time"
                                    );

                                    account.addCard(newCard);

                                    Transaction transaction = new Transaction(
                                            command.getTimestamp(),
                                            "New card created",
                                            command.getAccount(), generatedCardNumber,
                                            command.getEmail(),
                                            "one_time_card_creation", "1"
                                    );
                                    account.addTransaction(transaction);
                                    user.addTransaction(transaction);
                                }
                            }
                        }
                    }
                }
                case "deleteAccount" -> {
                    boolean accountDeleted = false;
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            List<Account> accounts = user.getAccounts();
                            for (int i = 0; i < accounts.size(); i++) {
                                Account account = accounts.get(i);
                                if (account.getIBAN().equals(command.getAccount())) {
                                    if (account.getBalance() == 0.0) {
                                        account.getCards().clear();
                                        accounts.remove(i);
                                        accountDeleted = true;
                                    } else {
                                        Transaction transaction = new Transaction(
                                                command.getTimestamp(),
                                                "Account couldn't be deleted -"
                                                        +
                                                        " there are funds remaining",
                                                "deleteAccount"
                                        );
                                        account.addTransaction(transaction);
                                        user.addTransaction(transaction);
                                    }
                                }
                            }
                        }
                    }

                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", "deleteAccount");
                    ObjectNode outputNode = objectMapper.createObjectNode();
                    if (accountDeleted) {
                        outputNode.put("success", "Account deleted");
                    } else {
                        outputNode.put("error", "Account couldn't be"
                                +
                                " deleted - see org.poo.transactions for details");
                    }
                    outputNode.put("timestamp", command.getTimestamp());
                    commandOutput.set("output", outputNode);
                    commandOutput.put("timestamp", command.getTimestamp());
                    output.add(commandOutput);
                }
                case "deleteCard" -> {
                    for (User user : users) {
                        String auxEmail = user.getEmail();
                        for (Account account : user.getAccounts()) {
                            String auxIBAN = account.getIBAN();
                            for (int i = 0; i < account.getCards().size(); i++) {
                                Card card = account.getCards().get(i);
                                if (card.getCardNUmber().equals(command.getCardNumber())) {
                                    // Șterge cardul din lista de carduri
                                    Card auxCard = account.getCards().get(i);
                                    account.getCards().remove(i);

                                    Transaction transaction = new Transaction(
                                            command.getTimestamp(),
                                            "The card has been destroyed",
                                            auxIBAN, // Nu este IBAN expeditor
                                            auxCard.getCardNUmber(),
                                            auxEmail,
                                            "card_deletion", "0"
                                    );
                                    account.addTransaction(transaction);
                                    user.addTransaction(transaction);
                                    break;
                                }
                            }
                        }
                    }
                }
                case "setMinimumBalance" -> {
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            for (Account account : user.getAccounts()) {
                                if (account.getIBAN().equals(command.getAccount())) {
                                    account.setMinBalance(command.getAmount());
                                    return;
                                }
                            }
                        }
                    }
                }
                case "checkCardStatus" -> {
                    boolean cardFound = false;
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            for (Card card : account.getCards()) {
                                if (card.getCardNUmber().equals(command.getCardNumber())) {
                                    cardFound = true;

                                    double balanceDifference = account.getBalance()
                                            - account.getMinBalance();

                                    if (account.getBalance() <= account.getMinBalance()) {
                                        card.setStatus("frozen");
                                        card.setIsBlocked(1);
                                        Transaction transaction = new Transaction(
                                                command.getTimestamp(),
                                                "You have reached the minimum "
                                                        +
                                                        "amount of funds, "
                                                        +
                                                        "the card will be frozen",
                                                "frozen card");
                                        account.addTransaction(transaction);
                                        user.addTransaction(transaction);
                                    } else if (balanceDifference <= 30) {
                                        card.setStatus("warning");
                                    } else if (account.getBalance() > account.getMinBalance()) {
                                        card.setStatus("active");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (!cardFound) {
                        ObjectNode commandOutput = objectMapper.createObjectNode();
                        commandOutput.put("command", "checkCardStatus");

                        ObjectNode outputNode = objectMapper.createObjectNode();
                        outputNode.put("description", "Card not found");
                        outputNode.put("timestamp", command.getTimestamp());

                        commandOutput.set("output", outputNode);
                        commandOutput.put("timestamp", command.getTimestamp());
                        output.add(commandOutput);
                    }
                }
                case "payOnline" -> {
                    /**
                     * comanda pentru plati online
                     */
                    boolean transactionSuccessful = false;
                    boolean cardFound = false;

                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            for (Account account : user.getAccounts()) {
                                for (Card card : account.getCards()) {
                                    // Verificăm dacă cardul corespunde și este activ
                                    if (card.getCardNUmber().equals(command.getCardNumber())
                                            && card.getIsBlocked() == 1) {
                                        Transaction transaction = new Transaction(
                                                command.getTimestamp(), "The card is frozen",
                                                "frozen card");
                                        account.addTransaction(transaction);
                                        user.addTransaction(transaction);
                                        transactionSuccessful = false;
                                        cardFound = true;
                                        break;
                                    } else if (card.getCardNUmber().equals(
                                            command.getCardNumber()) && card.
                                            getStatus().equals("active")) {
                                        cardFound = true;
                                        if (command.getAmount() == 0) {
                                            break;
                                        }
                                        double convertedAmount = command.getAmount();
                                        if (!account.getCurrency().equals(command.getCurrency())) {
                                            convertedAmount = Converter.getInstance().
                                                    convert(
                                                            command.getAmount(),
                                                            command.getCurrency(),
                                                            account.getCurrency(),
                                                            Arrays.asList(
                                                                    inputData.
                                                                            getExchangeRates())
                                                    );
                                        }

                                        if (account.getBalance() >= convertedAmount) {
                                            double newBalance =
                                                    account.getBalance() - convertedAmount;

                                            account.setBalance(newBalance);

                                            if (account.getPlan().equals("standard")) {
                                                double fee = convertedAmount * 0.002;
                                                double balance = account.getBalance() - fee;
                                                account.setBalance(balance);
                                            } else if (account.getPlan().equals("silver")) {
                                                double amountPay = Converter.getInstance().convert(
                                                        command.getAmount(),
                                                        command.getCurrency(), "RON",
                                                        Arrays.asList(
                                                                inputData.getExchangeRates()));
                                                if (amountPay >= MINIMUM_SILVER) {
                                                    double fee = convertedAmount * 0.001;
                                                    double balance = account.getBalance() - fee;
                                                    account.setBalance(balance);
                                                }
                                            }

                                            for (Commerciant commerciant : commerciants) {
                                                if (commerciant.getCommerciant()
                                                        .equals(command.getCommerciant())) {
                                                    CashbackContext cashbackContext =
                                                            new CashbackContext();
                                                    CashbackStrategy strategy;

                                                    if (commerciant.getType().equals("Clothes")) {
                                                        account.incrClothes();
                                                    } else if (commerciant.getType()
                                                            .equals("Food")) {
                                                        account.incrFood();
                                                    } else if (commerciant.getType()
                                                            .equals("Tech")) {
                                                        account.incrTech();
                                                    }
                                                    if (commerciant.getCashbackStrategy()
                                                            .equals("spendingThreshold")) {
                                                        double amount =
                                                                Converter.getInstance().convert(
                                                                command.getAmount(),
                                                                command.getCurrency(),
                                                                "RON",
                                                                Arrays.asList(
                                                                        inputData
                                                                                .getExchangeRates())
                                                        );

                                                        account.addToTotalSpent(amount);
                                                        strategy = new SpendingThresholdStrategy();
                                                    } else if (commerciant.getCashbackStrategy()
                                                            .equals("nrOfTransactions")) {
                                                        strategy = new NumberOfTransactions();
                                                    } else {
                                                        continue;
                                                    }
                                                    if (commerciant.getCashbackStrategy()
                                                            .equals("spendingThreshold")) {
                                                        cashbackContext.setStrategy(strategy);
                                                        double cashback = cashbackContext
                                                                .executeStrategy(convertedAmount,
                                                                account);
                                                        if (cashback > 0) {
                                                            double value = account.getBalance()
                                                                    + cashback;
                                                            account.setBalance(value);
                                                        }
                                                    } else if (commerciant.getCashbackStrategy()
                                                            .equals("nrOfTransactions")) {
                                                        cashbackContext.setStrategy(strategy);
                                                        double cashback = cashbackContext
                                                                .executeStrategy(
                                                                convertedAmount, account);
                                                        if (cashback > 0) {
                                                            double value = account.getBalance()
                                                                    + cashback;
                                                            account.setBalance(value);
                                                        }
                                                    }
                                                }
                                            }

                                            if (card.getTypeOfCard().equals("one_time")) {
                                                Transaction newCardTransaction = new Transaction(
                                                        command.getTimestamp(),
                                                        "Card payment",
                                                        command.getCommerciant(),
                                                        convertedAmount,
                                                        "online_payment"
                                                );
                                                account.addTransaction(newCardTransaction);
                                                user.addTransaction(newCardTransaction);
                                                Transaction deleteCard = new Transaction(
                                                        command.getTimestamp(),
                                                        "The card has been destroyed",
                                                        account.getIBAN(),
                                                        command.getCardNumber(),
                                                        command.getEmail(),
                                                        "card_deletion", "1"

                                                );
                                                account.addTransaction(deleteCard);
                                                user.addTransaction(deleteCard);
                                                account.getCards().remove(card);
                                                // Generăm un nou card de tip OneTimeCard
                                                String newCardNumber = Utils.generateCardNumber();
                                                Card newCard = new Card(newCardNumber, "active",
                                                        "one_time");
                                                account.addCard(newCard);

                                                Transaction secondTransaction = new Transaction(
                                                        command.getTimestamp(),
                                                        "New card created",
                                                        account.getIBAN(),
                                                        newCardNumber,
                                                        command.getEmail(),
                                                        "card_creation", "1"
                                                );
                                                account.addTransaction(secondTransaction);
                                                user.addTransaction(secondTransaction);
                                            } else {
                                                Transaction transaction = new Transaction(
                                                        command.getTimestamp(),
                                                        "Card payment",
                                                        command.getCommerciant(),
                                                        convertedAmount,
                                                        "online_payment"
                                                );
                                                account.addTransaction(transaction);
                                                user.addTransaction(transaction);
                                                transactionSuccessful = true;

                                            }
                                        } else {
                                            Transaction transaction = new Transaction(
                                                    command.getTimestamp(), "Insufficient funds",
                                                    "not enough money");
                                            account.addTransaction(transaction);
                                            user.addTransaction(transaction);
                                            transactionSuccessful = false;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (!transactionSuccessful) {
                        ObjectNode commandOutput = objectMapper.createObjectNode();
                        commandOutput.put("command", "payOnline");
                        ObjectNode outputNode = objectMapper.createObjectNode();
                        if (!cardFound) {
                            outputNode.put("timestamp", command.getTimestamp());
                            commandOutput.set("output", outputNode);
                            commandOutput.put("timestamp", command.getTimestamp());
                            outputNode.put("description", "Card not found");
                            output.add(commandOutput);
                        }
                    }
                }
                case "sendMoney" -> {
                    boolean senderFound = false;
                    boolean receiverFound = false;
                    Account senderAccount = null;
                    Account receiverAccount = null;
                    User senderUser = null;
                    User receiverUser = null;

                    // Găsim userul care trimite
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                senderFound = true;
                                senderAccount = account;
                                senderUser = user;
                                break;
                            }
                        }
                    }

                    if (senderUser == null) {
                        break;
                    }

                    // Găsim userul care primeste
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getReceiver())) {
                                receiverFound = true;
                                receiverAccount = account;
                                receiverUser = user;
                                break;
                            }
                        }
                    }


                    if (!receiverFound) {
                        ObjectNode commandOutput = objectMapper.createObjectNode();
                        commandOutput.put("command", "sendMoney");
                        ObjectNode outputNode = objectMapper.createObjectNode();
                        outputNode.put("description", "User not found");
                        outputNode.put("timestamp", command.getTimestamp());
                        commandOutput.set("output", outputNode);
                        commandOutput.put("timestamp", command.getTimestamp());
                        output.add(commandOutput);
                        break;
                    }

                    // Verificăm dacă expeditorul are suficiente fonduri
                    if (senderAccount.getBalance() < command.getAmount()) {
                        Transaction transaction = new Transaction(
                                command.getTimestamp(), "Insufficient funds",
                                "not_enough");
                        senderAccount.addTransaction(transaction);
                        senderUser.addTransaction(transaction);
                        break;
                    }

                    double convertedAmount = command.getAmount();
                    if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
                        convertedAmount = Converter.getInstance().convert(
                                command.getAmount(),
                                senderAccount.getCurrency(),
                                receiverAccount.getCurrency(),
                                Arrays.asList(inputData.getExchangeRates())
                        );
                    }

                    if (senderAccount.getBalance() <= command.getAmount()) {
                        Transaction transaction = new Transaction(
                                command.getTimestamp(), "Insufficient funds",
                                "not_enough");
                        senderAccount.addTransaction(transaction);
                        senderUser.addTransaction(transaction);
                        break;
                    }

                    senderAccount.setBalance(senderAccount.getBalance() - command.getAmount());
                    double newReceiverBalance = receiverAccount.getBalance() + convertedAmount;
                    receiverAccount.setBalance(newReceiverBalance);

                    if (senderAccount.getPlan().equals("standard")) {
                        double fee = command.getAmount() * 0.002;
                        double balance = senderAccount.getBalance() - fee;
                        senderAccount.setBalance(balance);
                    } else if (senderAccount.getPlan().equals("silver")) {
                        double amount = Converter.getInstance().convert(command.getAmount(),
                                senderAccount.getCurrency(), "RON",
                                Arrays.asList(inputData.getExchangeRates()));
                        if (amount >= MINIMUM_SILVER) {
                            double fee = command.getAmount() * 0.001;
                            double balance = senderAccount.getBalance() - fee;
                            senderAccount.setBalance(balance);
                        }
                    }

                    Transaction senderTransaction = new Transaction(
                            command.getTimestamp(),
                            command.getDescription(),
                            senderAccount.getIBAN(),
                            receiverAccount.getIBAN(),
                            command.getAmount() + " " + senderAccount.getCurrency(),
                            "sent"
                    );
                    senderAccount.addTransaction(senderTransaction);
                    senderUser.addTransaction(senderTransaction);

                    Transaction receiverTransaction = new Transaction(
                            command.getTimestamp(),
                            command.getDescription(),
                            senderAccount.getIBAN(),
                            receiverAccount.getIBAN(),
                            convertedAmount + " " + receiverAccount.getCurrency(),
                            "received"
                    );
                    receiverAccount.addTransaction(receiverTransaction);
                    receiverUser.addTransaction(receiverTransaction);
                }
                case "setAlias" -> {
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            for (Account account : user.getAccounts()) {
                                if (account.getIBAN().equals(command.getAccount())) {
                                    account.setAlias(command.getAlias());
                                    break;
                                }
                            }
                        }
                    }
                } case "changeInterestRate" -> {
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                if (account.getType().equals("savings")) {
                                    account.setInterestRate(command.getInterestRate());
                                    Transaction transaction = new Transaction(
                                            command.getTimestamp(),
                                            "Interest rate of the account changed to "
                                                    +
                                                    command.getInterestRate(),
                                            "changeInterestRate");
                                    account.addTransaction(transaction);
                                    user.addTransaction(transaction);
                                } else {
                                    ObjectNode commandOutput = objectMapper.createObjectNode();
                                    commandOutput.put("command", "changeInterestRate");
                                    ObjectNode outputNode = objectMapper.createObjectNode();
                                    outputNode.put("timestamp", command.getTimestamp());
                                    outputNode.put("description",
                                            "This is not a savings account");
                                    commandOutput.set("output", outputNode);
                                    commandOutput.put("timestamp", command.getTimestamp());
                                    output.add(commandOutput);
                                }
                            }
                        }
                    }

                } case "splitPayment" -> {
                    if (command.getSplitPaymentType().equals("custom")) {
                        splitPayment.setCommand(command.getCommand());
                        splitPayment.setSplitPaymentType(command.getSplitPaymentType());
                        splitPayment.setAccounts(command.getAccounts());
                        splitPayment.setAmount(command.getAmount());
                        splitPayment.setAmountForUsers(command.getAmountForUsers());
                        splitPayment.setCurrency(command.getCurrency());
                        splitPayment.setTimestamp(command.getTimestamp());
                        splitPayment.setNumberOfAccountsCommand(command.getAccounts().size());
                        splitPayment.setAux(command.getAmountForUsers());
                        splitPayment.setNumberOfAccountsInvolved(0);
                    } else if (command.getSplitPaymentType().equals("equal")) {
                        equalSplit.setCommand(command.getCommand());
                        equalSplit.setSplitPaymentType(command.getSplitPaymentType());
                        equalSplit.setAccounts(command.getAccounts());
                        equalSplit.setAmount(command.getAmount());
                        equalSplit.setCurrency(command.getCurrency());
                        equalSplit.setTimestamp(command.getTimestamp());
                        equalSplit.setNumberOfAccountsCommand(command.getAccounts().size());
                        equalSplit.setAux(command.getAmountForUsers());
                        equalSplit.setNumberOfAccountsInvolved(0);
                    }
                } case "addInterest" -> {
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())
                                    &&
                                    account.getType().equals("savings")) {
                                double save = account.getBalance() * account.getInterestRate();
                                account.setBalance(account.getBalance()
                                        +
                                        account.getInterestRate() * account.getBalance());
                                Transaction transaction = new Transaction(
                                        command.getTimestamp(),
                                        "Interest rate income", account.getCurrency(),
                                        save,
                                        "addInterest");
                                account.addTransaction(transaction);
                                user.addTransaction(transaction);
                            } else if (account.getIBAN().equals(command.getAccount())
                                    ||
                                    account.getType().equals("classical")) {
                                ObjectNode commandOutput = objectMapper.createObjectNode();
                                commandOutput.put("command", "addInterest");
                                ObjectNode outputNode = objectMapper.createObjectNode();
                                outputNode.put("timestamp", command.getTimestamp());
                                outputNode.put("description",
                                        "This is not a savings account");
                                commandOutput.set("output", outputNode);
                                commandOutput.put("timestamp", command.getTimestamp());
                                output.add(commandOutput);
                            }
                        }
                    }
                } case "report" -> {
                    Account targetAccount = null;
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                targetAccount = account;
                                break;
                            }
                        }
                        if (targetAccount != null) {
                            break;
                        }
                    }

                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", "report");

                    if (targetAccount == null) {
                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description", "Account not found");
                        errorOutput.put("timestamp", command.getTimestamp());
                        commandOutput.set("output", errorOutput);
                    } else {
                        ObjectNode accountReport = objectMapper.createObjectNode();
                        accountReport.put("IBAN", targetAccount.getIBAN());
                        accountReport.put("balance", targetAccount.getBalance());
                        accountReport.put("currency", targetAccount.getCurrency());

                        ArrayNode transactionsOutput = objectMapper.createArrayNode();

                        for (Transaction transaction : targetAccount.getTransactions()) {
                            if (transaction.getTimestamp()
                                    >=
                                    command.getStartTimestamp()
                                    &&
                                    transaction.getTimestamp()
                                            <=
                                            command.getEndTimestamp()) {
                                if (targetAccount.getType().equals("savings")
                                        &&
                                        (!transaction.getTransferType().
                                                equals("addInterest"))) {
                                    continue;
                                }

                                ObjectNode transactionNode = objectMapper.createObjectNode();
                                transactionNode.put("timestamp",
                                        transaction.getTimestamp());
                                transactionNode.put("description",
                                        transaction.getDescription());
                                if (transaction.getTransferType().
                                        equals("online_payment")) {
                                    transactionNode.put("commerciant",
                                            transaction.getCommerciant());
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                } else if (transaction.getTransferType().
                                        equals("card_creation")) {
                                    transactionNode.put("account",
                                            transaction.getAccount());
                                    transactionNode.put("card",
                                            transaction.getCardNumber());
                                    transactionNode.put("cardHolder",
                                            transaction.getCardHolder());
                                } else if (transaction.getTransferType().
                                        equals("sent")
                                        ||
                                        transaction.getTransferType().equals("received")) {
                                    transactionNode.put("amount",
                                            transaction.getAmount());
                                    transactionNode.put("description",
                                            transaction.getDescription());
                                    transactionNode.put("receiverIBAN",
                                            transaction.getReceiverIBAN());
                                    transactionNode.put("senderIBAN",
                                            transaction.getSenderIBAN());
                                    transactionNode.put("transferType",
                                            transaction.getTransferType());
                                } else if (transaction.getTransferType().
                                        equals("split_payment_error")) {
                                    transactionNode.put("amount",
                                            transaction.getAmountToPay());
                                    transactionNode.put("currency",
                                            transaction.getCurrency());
                                    transactionNode.put("error",
                                            "Account " + transaction.getError()
                                                    +
                                                    " has insufficient funds "
                                                    +
                                                    "for a split payment.");
                                    ArrayNode involvedAccounts = objectMapper.createArrayNode();
                                    for (String iban : transaction.getAccountsToSplit()) {
                                        involvedAccounts.add(iban);
                                    }
                                    transactionNode.set("involvedAccounts",
                                            involvedAccounts);
                                } else if (transaction.getTransferType().
                                        equals("one_time_card_creation")) {
                                    transactionNode.put("account",
                                            transaction.getAccount());
                                    transactionNode.put("card",
                                            transaction.getCardNumber());
                                    transactionNode.put("cardHolder",
                                            transaction.getCardHolder());
                                }
                                transactionsOutput.add(transactionNode);
                            }
                        }

                        accountReport.set("transactions", transactionsOutput);
                        commandOutput.set("output", accountReport);
                    }

                    commandOutput.put("timestamp", command.getTimestamp());
                    output.add(commandOutput);
                } case "spendingsReport" -> {
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", "spendingsReport");

                    Account targetAccount = null;
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                targetAccount = account;
                                break;
                            }
                        }
                        if (targetAccount != null) {
                            break;
                        }
                    }

                    if (targetAccount == null) {
                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description", "Account not found");
                        errorOutput.put("timestamp", command.getTimestamp());
                        commandOutput.set("output", errorOutput);
                    } else if (targetAccount.getType().equals("classic")) {
                        ObjectNode spendingsReport = objectMapper.createObjectNode();
                        spendingsReport.put("IBAN", targetAccount.getIBAN());
                        spendingsReport.put("balance", targetAccount.getBalance());
                        spendingsReport.put("currency", targetAccount.getCurrency());

                        Map<String, Double> merchantSpendings = new HashMap<>();
                        ArrayNode transactionsOutput = objectMapper.createArrayNode();

                        for (Transaction transaction : targetAccount.getTransactions()) {
                            if (transaction.getTransferType().equals("online_payment")
                                    &&
                                    transaction.getTimestamp() >= command.getStartTimestamp()
                                    &&
                                    transaction.getTimestamp() <= command.getEndTimestamp()) {

                                merchantSpendings.put(transaction.getCommerciant(),
                                        merchantSpendings.getOrDefault(
                                                transaction.getCommerciant(),
                                                0.0) + transaction.getAmountToPay());

                                ObjectNode transactionNode = objectMapper.createObjectNode();
                                transactionNode.put("timestamp", transaction.getTimestamp());
                                transactionNode.put("description", transaction.getDescription());
                                transactionNode.put("amount", transaction.getAmountToPay());
                                transactionNode.put("commerciant", transaction.getCommerciant());
                                transactionsOutput.add(transactionNode);
                            }
                        }

                        ArrayNode merchantsOutput = objectMapper.createArrayNode();
                        merchantSpendings.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .forEach(entry -> {
                                    ObjectNode merchantNode = objectMapper.createObjectNode();
                                    merchantNode.put("commerciant", entry.getKey());
                                    merchantNode.put("total", entry.getValue());
                                    merchantsOutput.add(merchantNode);
                                });

                        spendingsReport.set("commerciants", merchantsOutput);
                        spendingsReport.set("transactions", transactionsOutput);
                        commandOutput.set("output", spendingsReport);
                    } else {
                        ObjectNode spendingsReport = objectMapper.createObjectNode();
                        spendingsReport.put("error",
                                "This kind of report is not"
                                        +
                                        " supported for a saving account");
                        commandOutput.set("output", spendingsReport);
                    }

                    commandOutput.put("timestamp", command.getTimestamp());
                    output.add(commandOutput);
                } case "withdrawSavings" -> {
                    User currentUser = null;
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                currentUser = user;
                                break;
                            }
                        }
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate birthDate = LocalDate.parse(currentUser.getBirthDate(), formatter);
                    LocalDate currentDate = LocalDate.now();
                    int age = Period.between(birthDate, currentDate).getYears();

                    if (age < 21) {
                        Transaction transaction = new Transaction(
                                command.getTimestamp(),
                                "You don't have the minimum age required.",
                                "lessThan21"
                        );
                        currentUser.addTransaction(transaction);
                    }

                    Account savingsAccount = null;
                    Account targetClassicAccount = null;

                    for (Account account : currentUser.getAccounts()) {
                        if (account.getIBAN().equals(command.getAccount())
                                &&
                                account.getType().equals("savings")) {
                            savingsAccount = account;
                            break;
                        }
                    }

                    if (savingsAccount == null) {
                        break;
                    }

                    for (Account account : currentUser.getAccounts()) {
                        if (account.getType().equals("classic")) {
                            targetClassicAccount = account;
                            break;
                        }
                    }
                    if (targetClassicAccount == null) {
                        Transaction transaction = new Transaction(command.getTimestamp(),
                                "You do not have a classic account.",
                                "no classic account");
                        currentUser.addTransaction(transaction);
                        break;
                    }
                    if (savingsAccount.getBalance() < command.getAmount()) {
                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description",
                                "Insufficient funds in savings account");
                        errorOutput.put("timestamp", command.getTimestamp());
                        output.add(errorOutput);
                        break;
                    }

                    savingsAccount.setBalance(savingsAccount.getBalance()
                            +
                            targetClassicAccount.getBalance());
                    double convertedAmount = command.getAmount();

                    if (!savingsAccount.getCurrency().equals(command.getCurrency())) {
                        convertedAmount = Converter.getInstance().convert(
                                command.getAmount(),
                                savingsAccount.getCurrency(),
                                targetClassicAccount.getCurrency(),
                                Arrays.asList(inputData.getExchangeRates())
                        );
                    }
                } case "upgradePlan" -> {
                    User currentUser = null;
                    Account currentAccount = null;
                    boolean noUpdate = false;
                    boolean noMoney = false;
                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            if (account.getIBAN().equals(command.getAccount())) {
                                currentUser = user;
                                currentAccount = account;
                                break;
                            }
                        }
                        if (currentUser != null) {
                            break;
                        }
                        if (currentAccount != null) {
                            break;
                        }
                    }

                    if (currentUser == null) {
                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description", "User or account not found");
                        errorOutput.put("timestamp", command.getTimestamp());
                        output.add(errorOutput);
                        break;
                    }
                    String type = null;
                    if (currentAccount.getPlan().equals("standard")
                            ||
                            currentAccount.getPlan().equals("student")) {
                        if (command.getNewPlanType().equals("silver")) {
                            double convertedAmount =
                                    Converter.getInstance().convert(
                                    100, "RON",
                                    currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates())
                            );
                            double currentBalance = currentAccount.getBalance();

                            if (currentBalance >= convertedAmount) {
                                type = "silver";
                                currentAccount.setBalance(currentBalance - convertedAmount);
                                currentAccount.setPlan("silver");
                                for (Account account : currentUser.getAccounts()) {
                                    account.setPlan("silver");
                                }
                            } else {
                                noMoney = true;
                                break;
                            }
                        } else if (command.getNewPlanType().equals("gold")) {
                            double convertedAmount =
                                    Converter.getInstance().convert(
                                    350, "RON", currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates())
                            );

                            double currentBalance = currentAccount.getBalance();

                            if (currentBalance >= convertedAmount) {
                                currentAccount.setBalance(currentBalance - convertedAmount);
                                currentAccount.setPlan("gold");
                                for (Account account : currentUser.getAccounts()) {
                                    account.setPlan("gold");
                                }
                            } else {
                                noMoney = true;
                                break;
                            }
                        } else {
                            noUpdate = true;
                            break;
                        }
                    } else if (currentAccount.getPlan().equals("silver")) {
                        if (command.getNewPlanType().equals("gold")) {
                            double convertedAmount =
                                    Converter.getInstance().convert(
                                    250, "RON",
                                    currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates())
                            );

                            double currentBalance = currentAccount.getBalance();

                            if (currentBalance >= convertedAmount) {
                                currentAccount.setBalance(currentBalance - convertedAmount);
                                currentAccount.setPlan("gold");
                                for (Account account : currentUser.getAccounts()) {
                                    account.setPlan("gold");
                                }
                            } else {
                                noMoney = true;
                                break;
                            }
                        }
                    } else {
                        noUpdate = true;
                        break;
                    }
                    if (currentAccount.getPlan() == "silver") {
                        for (Account account : currentUser.getAccounts()) {
                            account.setPlan("silver");
                        }
                    }
                    if (!noUpdate || !noMoney) {
                        Transaction upgradeTransaction = new Transaction(
                                command.getTimestamp(),
                                "Upgrade plan",
                                currentAccount.getIBAN(),
                                command.getNewPlanType(),
                                "upgradeFee"
                        );
                        currentUser.addTransaction(upgradeTransaction);
                    }
                } case "cashWithdrawal" -> {
                    boolean done = false;
                    User currentUser = null;
                    Account currentAccount = null;
                    for (User user : users) {
                        if (user.getEmail().equals(command.getEmail())) {
                            currentUser = user;
                        }
                    }

                    if (currentUser == null) {
                        ObjectNode commandOutput = objectMapper.createObjectNode();
                        commandOutput.put("command", "cashWithdrawal");

                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description", "User not found");
                        errorOutput.put("timestamp", command.getTimestamp());

                        commandOutput.set("output", errorOutput);
                        commandOutput.put("timestamp", command.getTimestamp());

                        output.add(commandOutput);
                        break;
                    }

                    for (User user : users) {
                        for (Account account : user.getAccounts()) {
                            for (Card card : account.getCards()) {
                                if (card.getCardNUmber().equals(command.getCardNumber())) {
                                    currentAccount = account;
                                    break;
                                }
                            }
                        }
                    }

                    if (currentAccount == null) {
                        ObjectNode commandOutput = objectMapper.createObjectNode();
                        commandOutput.put("command",
                                "cashWithdrawal");

                        ObjectNode errorOutput = objectMapper.createObjectNode();
                        errorOutput.put("description", "Card not found");
                        errorOutput.put("timestamp", command.getTimestamp());

                        commandOutput.set("output", errorOutput);
                        commandOutput.put("timestamp", command.getTimestamp());

                        output.add(commandOutput);
                        break;
                    }
                    double amount = currentAccount.getBalance();
                    double convertedAmount =
                            Converter.getInstance().convert(
                            amount, currentAccount.getCurrency(),
                                    "RON",
                            Arrays.asList(
                                    inputData.getExchangeRates())
                    );

                    if (convertedAmount >= command.getAmount()) {
                        if (currentAccount.getPlan().equals("standard")) {
                            double fee = command.getAmount() * 0.002;
                            double necessaryAmount =
                                    Converter.getInstance().convert(
                                    command.getAmount(), "RON",
                                    currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates()));
                            double convertedFee =
                                    Converter.getInstance().convert(
                                    fee, "RON",
                                    currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates()));
                            double value = currentAccount.getBalance()
                                    -
                                    necessaryAmount
                                    -
                                    convertedFee;
                            currentAccount.setBalance(value);
                        } else if (currentAccount.getPlan().equals("silver")) {
                            if (command.getAmount() >= 500) {
                                double fee = command.getAmount() * 0.001;
                                double necessaryAmount =
                                        Converter.getInstance().convert(
                                        command.getAmount(), "RON",
                                        currentAccount.getCurrency(),
                                        Arrays.asList(inputData.getExchangeRates()));
                                double convertedFee =
                                        Converter.getInstance().convert(
                                        fee, "RON",
                                        currentAccount.getCurrency(),
                                        Arrays.asList(inputData.getExchangeRates()));
                                double value = currentAccount.getBalance()
                                        -
                                        necessaryAmount - convertedFee;
                                currentAccount.setBalance(value);
                            }
                        } else {
                            double necessaryAmount =
                                    Converter.getInstance().convert(
                                    command.getAmount(), "RON",
                                    currentAccount.getCurrency(),
                                    Arrays.asList(inputData.getExchangeRates()));
                            currentAccount.setBalance(
                                    currentAccount.getBalance()
                                    -
                                    necessaryAmount);
                        }
                        done = true;
                    } else {
                        Transaction newTransaction = new Transaction(command.getTimestamp(),
                                "Insufficient funds", "noCash");
                        currentUser.addTransaction(newTransaction);
                    }
                    if (done) {
                        Transaction transaction = new Transaction(command.getTimestamp(),
                                "Cash withdrawal of " + command.getAmount(),
                                command.getAmount(), "cashWithdrawal");
                        currentUser.addTransaction(transaction);
                    }
                } case "acceptSplitPayment" -> {
                    if (command.getSplitPaymentType().equals("custom")) {
                        if (splitPayment.getAccounts() == null) {
                            break;
                        }

                        if (splitPayment.getCommand() != null) {
                            if (!splitPayment.getSplitPaymentType().equals("custom")) {
                                break;
                            }
                        }

                        int i = 0;

                        if (splitPayment.getCommand() != null) {
                            for (String payingAccount : splitPayment.getAccounts()) {
                                for (User user : users) {
                                    for (Account account : user.getAccounts()) {
                                        if (account.getIBAN().equals(payingAccount)
                                                &&
                                                splitPayment.getSplitPaymentType().
                                                        equals("custom")) {
                                            account.setAcceptance("yes");
                                            splitPayment.setNumberOfAccountsInvolved(
                                                    splitPayment.getNumberOfAccountsInvolved()
                                                            + 1);
                                        }
                                    }
                                }
                            }
                        }

                        if (splitPayment.getCommand() != null) {
                            for (String payingAccount : splitPayment.getAccounts()) {
                                for (User user : users) {
                                    for (Account account : user.getAccounts()) {
                                        if (account.getIBAN().equals(payingAccount)) {
                                            if (account.getAcceptance().equals("yes")) {
                                                splitPayment.setNumberOfAccounts(
                                                        splitPayment.getNumberOfAccounts() + 1);
                                            }
                                        }
                                        break;
                                    }
                                    break;
                                }
                            }
                        }

                        Transaction transaction = new Transaction(
                                splitPayment.getTimestamp(), splitPayment.getAccounts(),
                                splitPayment.getCurrency(),
                                String.format("Split payment of %.2f %s",
                                        splitPayment.getAmount(),
                                        splitPayment.getCurrency()),
                                splitPayment.getAmountForUsers(),
                                splitPayment.getSplitPaymentType(),
                                "splitAccepted");

                        if (splitPayment.getNumberOfAccounts()
                                ==
                                splitPayment.getNumberOfAccountsCommand()) {
                            for (String payingAccount : splitPayment.getAccounts()) {
                                for (User user : users) {
                                    for (Account account : user.getAccounts()) {
                                        if (account.getIBAN().equals(payingAccount)) {
                                            if (splitPayment.getAux().get(i) != null) {
                                                double convertedAmount =
                                                        Converter.getInstance().convert(
                                                        splitPayment.getAux().get(i),
                                                        splitPayment.getCurrency(),
                                                        account.getCurrency(),
                                                        Arrays.asList(inputData.getExchangeRates())
                                                );
                                                if (account.getBalance() <= convertedAmount) {
                                                    splitPayment.setNumberOfPoorAccounts(
                                                            splitPayment.getNumberOfPoorAccounts()
                                                                    + 1);
                                                    account.setTooPoorToSplit("true");
                                                    splitPayment.setFirstAccount(payingAccount);
                                                    break;
                                                }
                                                i++;
                                                if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                                    break;
                                                }
                                            }
                                            if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                                break;
                                            }
                                        }
                                        if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                            break;
                                        }
                                    }
                                    if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                        break;
                                    }
                                }
                                if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                    break;
                                }
                            }
                        }
                        Transaction errorOut = new Transaction(
                                splitPayment.getTimestamp(), splitPayment.getAccounts(),
                                splitPayment.getCurrency(),
                                String.format(String.format("Split payment of %.2f %s",
                                                splitPayment.getAmount(),
                                                splitPayment.getCurrency()),
                                splitPayment.getAmount(), splitPayment.getCurrency()),
                                splitPayment.getAmountForUsers(),
                                "Account " + splitPayment.getFirstAccount()
                                + " has insufficient funds for a split payment.",
                                splitPayment.getSplitPaymentType(),
                                "noMoneyToSplit");

                        Transaction equalTransaction = new Transaction(
                                splitPayment.getTimestamp(), "Noew",
                                "changeInterestRate");

                        if (splitPayment.getNumberOfAccounts()
                                ==
                                splitPayment.getNumberOfAccountsCommand()) {
                            if (splitPayment.getCommand() != null) {

                                if (splitPayment.getNumberOfPoorAccounts() != 0) {
                                    for (String payingAccount : splitPayment.getAccounts()) {
                                        for (User user : users) {
                                            for (Account account : user.getAccounts()) {
                                                if (account.getIBAN().equals(payingAccount)) {
                                                    user.addTransaction(errorOut);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (splitPayment.getCommand() != null) {
                                        for (String payingAccount : splitPayment.getAccounts()) {
                                            for (User user : users) {
                                                for (Account account : user.getAccounts()) {
                                                    if (account.getIBAN().equals(payingAccount)
                                                            &&
                                                            command.getSplitPaymentType().
                                                                    equals("custom")) {
                                                        user.addTransaction(transaction);
                                                    } else if (command.
                                                            getSplitPaymentType().equals("equal")) {
                                                        user.addTransaction(equalTransaction);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
//                    } else if (command.getSplitPaymentType().equals("equal")) {
//                        if (equalSplit.getCommand() != null) {
//                            for (String payingAccount : equalSplit.getAccounts()) {
//                                for (User user : users) {
//                                    for (Account account : user.getAccounts()) {
//                                        if (account.getIBAN().equals(payingAccount)) {
//                                            account.setAcceptance("yes");
//                                            equalSplit.setNumberOfAccountsInvolved(
//                                                    equalSplit.getNumberOfAccountsInvolved() + 1);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        if (equalSplit.getCommand() != null) {
//                            for (String payingAccount : equalSplit.getAccounts()) {
//                                for (User user : users) {
//                                    for (Account account : user.getAccounts()) {
//                                        if (account.getIBAN().equals(payingAccount)) {
//                                            if (account.getAcceptance().equals("yes")) {
//                                                equalSplit.setNumberOfAccounts(
//                                                        equalSplit.getNumberOfAccounts() + 1);
//                                            }
//                                        }
//                                        break;
//                                    }
//                                    break;
//                                }
//                            }
//                        }
//
//                        if (equalSplit.getNumberOfAccounts()
//                                ==
//                                equalSplit.getNumberOfAccountsCommand()) {
//                            for (String payingAccount : equalSplit.getAccounts()) {
//                                for (User user : users) {
//                                    for (Account account : user.getAccounts()) {
//                                        if (account.getIBAN().equals(payingAccount)) {
//                                                double convertedAmount = Converter.
//                                                        getInstance().convert(
//                                                        equalSplit.getAmount()
//                                                                /
//                                                                equalSplit.getAccounts().size(),
//                                                        equalSplit.getCurrency(),
//                                                        account.getCurrency(),
//                                                        Arrays.asList(inputData.getExchangeRates())
//                                                );
//                                                if (account.getBalance() <= convertedAmount) {
//                                                    equalSplit.setNumberOfPoorAccounts(
//                                                            equalSplit.getNumberOfPoorAccounts()
//                                                                    + 1);
//                                                    account.setTooPoorToSplit("true");
//                                                    equalSplit.setFirstAccount(payingAccount);
//                                                    break;
//                                                }
//                                            if (equalSplit.getNumberOfPoorAccounts() != 0) {
//                                                break;
//                                            }
//                                        }
//                                        if (equalSplit.getNumberOfPoorAccounts() != 0) {
//                                            break;
//                                        }
//                                    }
//                                    if (equalSplit.getNumberOfPoorAccounts() != 0) {
//                                        break;
//                                    }
//                                }
//                                if (equalSplit.getNumberOfPoorAccounts() != 0) {
//                                    break;
//                                }
//                            }
//                        }
//
//                        Transaction errorOut = new Transaction(
//                                equalSplit.getTimestamp(), equalSplit.getAccounts(),
//                                equalSplit.getCurrency(), String.format(String.format(
//                                        "Split payment of %.2f %s", equalSplit.getAmount(),
//                                        equalSplit.getCurrency()),
//                                equalSplit.getAmount(), equalSplit.getCurrency()),
//                                equalSplit.getAmount() / equalSplit.getAccounts().size(),
//                                "Account " + equalSplit.getFirstAccount()
//                                + " has insufficient funds for a split payment.",
//                                equalSplit.getSplitPaymentType(),
//                                "noMoneyToSplit");
//
//
//                        if (equalSplit.getNumberOfAccounts()
//                                ==
//                                equalSplit.getNumberOfAccountsCommand()) {
//                            if (equalSplit.getCommand() != null) {
//
//                                if (equalSplit.getNumberOfPoorAccounts() != 0) {
//                                    for (String payingAccount : equalSplit.getAccounts()) {
//                                        for (User user : users) {
//                                            for (Account account : user.getAccounts()) {
//                                                if (account.getIBAN().equals(payingAccount)) {
//                                                    user.addTransaction(errorOut);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
                    }
                }
                default -> {
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", command.getCommand());
                    commandOutput.put("error", "Command not implemented");
                    commandOutput.put("timestamp", command.getTimestamp());
                    output.add(commandOutput);
                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    /**
     * Method used for extracting the test number from the file name.
     *
     * @param file the input file
     * @return the extracted numbers
     */
    public static int fileConsumer(final File file) {
        return Integer.parseInt(
                file.getName()
                        .replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR)
        );
    }
}
