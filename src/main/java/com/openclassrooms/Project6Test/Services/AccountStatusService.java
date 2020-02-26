package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.AccountStatus;
import com.openclassrooms.Project6Test.Repositories.AccountStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountStatusService {

    @Autowired
    private AccountStatusRepository accountStatusRepository;


    public void createAccountStatusTypes(List<String> accountStatusList) {

        accountStatusRepository.deleteAll();

        for (String accountStatus: accountStatusList) {

            AccountStatus accountStatusInstance = new AccountStatus(accountStatus);

            accountStatusRepository.save(accountStatusInstance);
        }
    }

    public void addAccountStatus(String accountStatus) {

        AccountStatus accountStatusInstance = new AccountStatus(accountStatus);

        accountStatusRepository.save(accountStatusInstance);
    }

    public List<String> getAllAccountStatus() {

        List<String> accountStatusList = null;

        if(!accountStatusRepository.findAll().isEmpty()) {
            for (AccountStatus accountStatus : accountStatusRepository.findAll()) {

                accountStatusList.add(accountStatus.getAccountStatus());
            }
        }

        return accountStatusList;
    }

    public String getAccountStatusById(int accountStatusId) {

        return accountStatusRepository.findAccountStatusById(accountStatusId).getAccountStatus();
    }

    public void deleteAccountStatus(String accountStatus) {

        accountStatusRepository.delete(accountStatusRepository.findAccountStatusByAccountStatus(accountStatus));
    }
}
