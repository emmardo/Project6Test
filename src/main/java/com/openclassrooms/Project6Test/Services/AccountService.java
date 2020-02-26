package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }


    public Account getAccountByUserEmail(String email) {

        return accountRepository.findAccountByUserEmail(email);
    }


    /*public List<Account> getAllAccountsByType(String accountType) {

        return accountRepository.findAll().stream()
                .filter(a -> a.getAccountType().getAccountType().equals(accountType))
                .collect(Collectors.toList());
    }


    public void updateAccountStatus(String email, String newStatus) {

        Account account = accountRepository.findAccountByUserEmail(email);

        AccountStatus newAccountStatus = new AccountStatus(newStatus);

        account.setAccountStatus(newAccountStatus);

        accountRepository.save(account);
    }*/
}
