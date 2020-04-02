package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {


    private TransactionRepository transactionRepository;

    private TransactionTypeRepository transactionTypeRepository;

    private AccountRepository accountRepository;

    private ConnectionRepository connectionRepository;

    private IbanRepository ibanRepository;

    //SET COMPANY'S ACCOUNT EMAIL!!!
    String companysEmail = "000@000.com";

    //FEE SET TO 0.5% AS FLOAT!!!
    float feeCostInPercentage = 0.5f;
    float feeCalculationFactor = feeCostInPercentage/100;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              TransactionTypeRepository transactionTypeRepository, AccountRepository accountRepository,
                              ConnectionRepository connectionRepository, IbanRepository ibanRepository) {

        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.accountRepository = accountRepository;
        this.connectionRepository = connectionRepository;
        this.ibanRepository = ibanRepository;
    }

    public void createTransactionByTransactionType(String transactionTypeString, String sendersEmail,
                                                   float moneyAmount, String receiversEmailOrIbanOrOrigin, String description) {

        if(transactionConditionsValidator(transactionTypeString, sendersEmail, moneyAmount,
                receiversEmailOrIbanOrOrigin)){

            Account sendersAccount = accountRepository.findAccountByUserEmail(sendersEmail);

            float sendersBalanceBeforeTransaction = sendersAccount.getCurrentBalance();

            Date madeAt = new Date();

            TransactionType transactionType = transactionTypeRepository
                                                .findTransactionTypeByTransactionType(transactionTypeString);

            Transaction newTransaction = new Transaction(transactionType, sendersAccount, moneyAmount, madeAt);
            newTransaction.setSendersBalanceBeforeTransaction(sendersBalanceBeforeTransaction);

            if(!transactionTypeString.equals("TopUp")) {

                sendersAccount.setCurrentBalance(sendersBalanceBeforeTransaction + moneyAmount);
                accountRepository.save(sendersAccount);
            }

            if(transactionTypeString.equals("Regular")){

                Account receiversAccount = accountRepository.findAccountByUserEmail(receiversEmailOrIbanOrOrigin);
                float receiversBalanceBeforeTransaction = receiversAccount.getCurrentBalance();

                Account companysAccount = accountRepository.findAccountByUserEmail(companysEmail);
                float companysBalanceBeforeTransaction = companysAccount.getCurrentBalance();

                receiversAccount.setCurrentBalance(receiversBalanceBeforeTransaction + moneyAmount);
                accountRepository.save(receiversAccount);

                sendersAccount.setCurrentBalance(sendersBalanceBeforeTransaction
                        - (moneyAmount * (1 + feeCalculationFactor)));
                accountRepository.save(sendersAccount);

                companysAccount.setCurrentBalance(companysBalanceBeforeTransaction
                        + (moneyAmount * feeCalculationFactor));
                accountRepository.save(companysAccount);

                Connection newConnection = connectionRepository.findConnectionByUserEmail(receiversEmailOrIbanOrOrigin);
                newTransaction.setConnection(newConnection);
                newTransaction.setReceiversBalanceBeforeTransaction(receiversBalanceBeforeTransaction);
                newTransaction.setDescription(description);

            }else if(transactionTypeString.equals("TopUp")) {

                sendersAccount.setCurrentBalance(sendersBalanceBeforeTransaction + moneyAmount);
                accountRepository.save(sendersAccount);

                newTransaction.setOrigin(receiversEmailOrIbanOrOrigin);
                newTransaction.setDescription("TopUp");

            }else if(transactionTypeString.equals("Withdrawal")) {

                if(ibanRepository.findByAccount_UserEmail(sendersEmail).stream()
                        .noneMatch(i -> i.getIbanString().equals(receiversEmailOrIbanOrOrigin))) {

                    Iban newIban = new Iban(sendersAccount, receiversEmailOrIbanOrOrigin);

                    newTransaction.setIban(newIban);

                    ibanRepository.save(newIban);

                }else{

                    Iban iban = ibanRepository.findByAccount_UserEmail(sendersEmail).stream()
                                .filter(i -> i.getIbanString().equals(receiversEmailOrIbanOrOrigin)).findFirst().get();

                    newTransaction.setIban(iban);
                }

                newTransaction.setDescription("Withdrawal");

                sendersAccount.setCurrentBalance(sendersBalanceBeforeTransaction - moneyAmount);
                accountRepository.save(sendersAccount);
            }
            transactionRepository.save(newTransaction);
        }
    }


    //CORRECT TO BE ABLE TO GET ALL TRANSACTIONS

    //Added "Receiver" as Transaction Type to show Transactions received by User
    //Transaction Types: "Regular", "TopUp", "Withdrawal" and "Receiver"
    public List<Transaction> getAUsersTransactionsByEmail(String userEmail) {

        List<Transaction> list = new ArrayList<>();

        if(!transactionRepository.findTransactionsByAccountUserEmail(userEmail).isEmpty()) {

            list.addAll(transactionRepository.findTransactionsByAccountUserEmail(userEmail));
        }
        if(!transactionRepository.findTransactionsByConnectionUserEmail(userEmail).isEmpty()) {

            list.addAll(transactionRepository.findTransactionsByConnectionUserEmail(userEmail));
        }

        return list;
    }


    /*public List<Transaction> filterTransactionsByTransactionType(String transactionType) {

        List<Transaction> newList = new ArrayList<>();

        if(transactionTypeValidator(transactionType)) {

            if(!transactionType.equals("All")) {

                if(transactionType.equals("Receiver")){

                    newList = transactionRepository.findAll().stream().filter(t -> t.getTransactionType()
                            .getTransactionType().equals("Regular")).collect(Collectors.toList());
                }else {

                    newList = transactionRepository.findAll().stream().filter(t -> t.getTransactionType()
                            .getTransactionType().equals(transactionType)).collect(Collectors.toList());
                }

            }else{

                newList = transactionRepository.findAll();
            }
        }

        return newList;
    }*/


    public boolean transactionConditionsValidator(String transactionTypeString, String sendersEmail,
                                                  float moneyAmount, String receiversEmailOrIbanOrOrigin) {
        boolean value = false;

        if(transactionTypeValidator(transactionTypeString) && activeAccountValidator(sendersEmail)) {

            float balanceAvailableMinusTransaction = (accountRepository.findAccountByUserEmail(sendersEmail)
                    .getCurrentBalance() - moneyAmount);

            if(transactionTypeString.equals("TopUp")) {

                value = true;

            }else{

                if(balanceAvailableMinusTransaction >= 0) {

                    if(transactionTypeString.equals("Withdrawal")) {

                        value = true;

                    }else{

                        if(activeAccountValidator(receiversEmailOrIbanOrOrigin)
                                && (balanceAvailableMinusTransaction - (moneyAmount*feeCalculationFactor)) >= 0) {

                            value = true;
                        }
                    }
                }
            }
        }
        return value;
    }


    //Added "Receiver" to Validate transactions received
    public boolean transactionTypeValidator(String transactionType) {

        boolean value = false;

        if(Arrays.asList("Regular", "TopUp", "Withdrawal", "All", "Receiver").contains(transactionType)) {

            value = true;
        }

        return value;
    }


    public boolean activeAccountValidator(String userEmail) {

        boolean value = false;

        if(accountRepository.findAccountByUserEmail(userEmail).getAccountStatus().getAccountStatus()
                .equals("Active")) {

            value = true;
        }

        return value;
    }


    /*public Transaction getTransactionById(int transactionId) {

        return transactionRepository.findById(transactionId).get();
    }

    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }*/


    /*public void createRegularTransaction(String senderUserEmail, String receiverUserEmail, float moneyAmount) {

        Account senderAccount = accountRepository.findAccountByUserEmail(senderUserEmail);

        Account receiverAccount = accountRepository.findAccountByUserEmail(receiverUserEmail);

        if(senderAccount.getAccountStatus().getAccountStatus().equals("Active")
                && receiverAccount.getAccountStatus().getAccountStatus().equals("Active")
                && (senderAccount.getCurrentBalance() - moneyAmount) >= 0) {

            float sendersBalanceBeforeTransaction = senderAccount.getCurrentBalance();
            senderAccount.setCurrentBalance(sendersBalanceBeforeTransaction - moneyAmount);
            accountRepository.save(senderAccount);


            float receiversBalanceBeforeTransaction = receiverAccount.getCurrentBalance();
            receiverAccount.setCurrentBalance(receiversBalanceBeforeTransaction + moneyAmount);
            accountRepository.save(receiverAccount);

           *//* String transactionTypeString = "Regular";
            TransactionType transactionType = new TransactionType(transactionTypeString);*//*

            Connection newConnection = connectionRepository.findConnectionByUserEmail(receiverUserEmail);

            *//*Date madeAt = new Date();*//*

     *//*Transaction newTransaction = new Transaction(transactionType, senderAccount, moneyAmount, madeAt);*//*
            newTransaction.setConnection(newConnection);
            newTransaction.setSendersBalanceBeforeTransaction(sendersBalanceBeforeTransaction);
            newTransaction.setReceiversBalanceBeforeTransaction(receiversBalanceBeforeTransaction);
            transactionRepository.save(newTransaction);
        }
    }

    // cambiar nombre del float y sacar origin
    public void createTopUpTransaction(String userEmail, float moneyAmount, String origin) {

        Account account = accountRepository.findAccountByUserEmail(userEmail);

        if(account.getAccountStatus().getAccountStatus().equals("Active")) {

            float balanceBeforeTransaction = account.getCurrentBalance();
            account.setCurrentBalance(balanceBeforeTransaction + moneyAmount);
            accountRepository.save(account);

           *//* String transactionTypeString = "TopUp";
            TransactionType transactionType = new TransactionType(transactionTypeString);

            Date madeAt = new Date();

            Transaction newTransaction = new Transaction(transactionType, account, moneyAmount, madeAt);*//*
            newTransaction.setSendersBalanceBeforeTransaction(balanceBeforeTransaction);
            newTransaction.setOrigin(origin);
            transactionRepository.save(newTransaction);
        }
    }


    public void createWithdrawalTransaction(String userEmail, String ibanString, float moneyAmount) {

        Account account = accountRepository.findAccountByUserEmail(userEmail);

        if(account.getAccountStatus().getAccountStatus().equals("Active")
                && (account.getCurrentBalance() - moneyAmount) >= 0) {

            float balanceBeforeTransaction = account.getCurrentBalance();
            account.setCurrentBalance(balanceBeforeTransaction - moneyAmount);
            accountRepository.save(account);

            *//*String transactionTypeString = "Withdrawal";
            TransactionType transactionType = new TransactionType(transactionTypeString);

            Date madeAt = new Date();*//*

            if(ibanRepository.findByAccount_UserEmail(userEmail).stream().noneMatch(i -> i.getIban().equals(ibanString))) {

                Iban newIban = new Iban(account, ibanString);

                ibanRepository.save(newIban);
            }

            *//*Transaction newTransaction = new Transaction(transactionType, account, moneyAmount, madeAt);*//*
            newTransaction.setIban(new Iban(account, ibanString));
            newTransaction.setSendersBalanceBeforeTransaction(balanceBeforeTransaction);
            transactionRepository.save(newTransaction);
        }
    }*/


    /*public List<Transaction> getAllRegularTransactionsAsSenderByUserEmail(String userEmail) {

        List<Transaction> newList = transactionRepository.findTransactionsByAccountUserEmail(userEmail).stream()
                                        .filter(t -> t.getTransactionType().getTransactionType().equals("Regular"))
                                        .collect(Collectors.toList());

        return newList;
    }


    public List<Transaction> getAllRegularTransactionsAsReceiverByUserEmail(String userEmail) {

        List<Transaction> newList = transactionRepository.findTransactionsByConnectionUserEmail(userEmail);

        return newList;
    }


    public List<Transaction> getAllRegularTransactionsByUserEmail(String userEmail) {

        List<Transaction> newList = new ArrayList<>();

        newList.addAll(getAllRegularTransactionsAsSenderByUserEmail(userEmail));
        newList.addAll(getAllRegularTransactionsAsReceiverByUserEmail(userEmail));

        return newList;
    }


    public List<Transaction> getAllTopUpTransactionsByUserEmail(String userEmail) {

        List<Transaction> newList = transactionRepository.findTransactionsByAccountUserEmail(userEmail).stream()
                                        .filter(t -> t.getTransactionType().getTransactionType().equals("TopUp"))
                                        .collect(Collectors.toList());

        return newList;
    }


    public List<Transaction> getAllWithdrawalTransactionsByUserEmail(String userEmail) {

        List<Transaction> newList = transactionRepository.findTransactionsByAccountUserEmail(userEmail).stream()
                                        .filter(t -> t.getTransactionType().getTransactionType().equals("Withdrawal"))
                                        .collect(Collectors.toList());

        return newList;
    }


    public List<Transaction> getAllTopUpAndWithdrawalTransactionsByUserEmail(String userEmail) {

        List<Transaction> newList = new ArrayList<>();

        newList.addAll(getAllTopUpTransactionsByUserEmail(userEmail));
        newList.addAll(getAllWithdrawalTransactionsByUserEmail(userEmail));

        return newList;
    }


    public List<Transaction> getAllUserTransactionsByUserEmail(String userEmail) {

        List<Transaction> newList = new ArrayList<>();

        newList.addAll(getAllRegularTransactionsAsSenderByUserEmail(userEmail));
        newList.addAll(getAllRegularTransactionsAsReceiverByUserEmail(userEmail));
        newList.addAll(getAllTopUpTransactionsByUserEmail(userEmail));
        newList.addAll(getAllWithdrawalTransactionsByUserEmail(userEmail));

        return newList;
    }*/


    /*public List<Transaction> getAllTransactionsByAccountAndTransactionType(String accountType,
                                                                           String transactionType) {

        List<Transaction> newList = new ArrayList<>();

        if(accountTypeValidator(accountType) && transactionTypeValidator(transactionType)) {

            List<Transaction> filteredList = filterTransactionsByTransactionType(transactionType);

            newList = filteredList.stream().filter(t -> t.getAccount().getAccountType().getAccountType()
                        .equals(accountType)).collect(Collectors.toList());
        }

        return newList;
    }*/

    /*//CHECK IF I NEED IT
    public boolean accountTypeValidator(String accountType) {

        boolean value = false;

        if(Arrays.asList("Regular", "Company").contains(accountType)) {

            value = true;
        }

        return value;
    }*/


    /*public List<Transaction> getAllRegularAccountsRegularTransactions() {

        return getAllRegularAccountsTransactions().stream()
                .filter(t -> t.getTransactionType().getTransactionType().equals("Regular"))
                .collect(Collectors.toList());
    }


    public List<Transaction> getAllRegularAccountsTopUpTransactions() {

        return getAllRegularAccountsTransactions().stream()
                .filter(t -> t.getTransactionType().getTransactionType().equals("TopUp"))
                .collect(Collectors.toList());
    }


    public List<Transaction> getAllRegularAccountsWithdrawalTransactions() {

        return getAllRegularAccountsTransactions().stream()
                .filter(t -> t.getTransactionType().getTransactionType().equals("Withdrawal"))
                .collect(Collectors.toList());
    }


    public List<Transaction> getAllRegularAccountsCancellationTransactions() {

        return getAllRegularAccountsTransactions().stream()
                .filter(t -> t.getTransactionType().getTransactionType().equals("Cancellation"))
                .collect(Collectors.toList());
    }*/
}
