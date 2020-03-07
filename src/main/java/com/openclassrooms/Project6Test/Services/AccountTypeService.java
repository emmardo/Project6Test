package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.AccountType;
import com.openclassrooms.Project6Test.Repositories.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeService {


    private AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {

        this.accountTypeRepository = accountTypeRepository;
    }


    public void createAccountTypes(List<String> accountTypesList) {

        accountTypeRepository.deleteAll();

        for (String accountType: accountTypesList) {

            AccountType accountTypeInstance = new AccountType(accountType);

            accountTypeRepository.save(accountTypeInstance);
        }
    }

    /*public void addAccountType(String accountType) {

        AccountType accountTypeInstance = new AccountType(accountType);

        accountTypeRepository.save(accountTypeInstance);
    }*/

    public List<String> getAllAccountTypes() {

        List<String> accountTypes = null;

        if(!accountTypeRepository.findAll().isEmpty()) {
            for (AccountType accountType : accountTypeRepository.findAll()) {

                accountTypes.add(accountType.getAccountType());
            }
        }

        return accountTypes;
    }

    /*public String getAccountTypeById(int accountTypeId) {

        return accountTypeRepository.findAccountTypeById(accountTypeId).getAccountType();
    }

    public void deleteAccountType(String accountType) {

        accountTypeRepository.delete(accountTypeRepository.findAccountTypeByAccountType(accountType));
    }*/
}
