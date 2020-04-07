package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
}
