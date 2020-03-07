package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Iban;
import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import com.openclassrooms.Project6Test.Repositories.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IbanService {

    private AccountRepository accountRepository;

    private IbanRepository ibanRepository;


    @Autowired
    public IbanService(AccountRepository accountRepository, IbanRepository ibanRepository) {

        this.accountRepository = accountRepository;
        this.ibanRepository = ibanRepository;
    }

    public void createIban(String email, String iban) {

        Account account = accountRepository.findAccountByUserEmail(email);

        Iban newIban = new Iban(account, iban);

        ibanRepository.save(newIban);
    }


   /* public Iban getIbanById(int ibanId) {

        return ibanRepository.findById(ibanId);
    }*/


    /*public List<Iban> getAllIbans() {

        return ibanRepository.findAll();
    }*/


    public List<Iban> getAllIbansByEmail(String email) {

        return ibanRepository.findByAccount_UserEmail(email);
    }


    /*public void updateIban(String existingIban, String newIban) {

        Iban iban = ibanRepository.findAll().stream().filter(i -> i.getIban().equals(existingIban)).findFirst().get();

        iban.setIban(newIban);

        ibanRepository.save(iban);
    }*/


    /*public void deleteIbanById(int ibanId) {

        ibanRepository.deleteById(ibanId);
    }


    public void deleteIbanByIban(String iban) {

        ibanRepository.deleteIbanByIban(iban);
    }*/


    /*public void deleteAllIbanByEmail(String email) {

        ibanRepository.deleteAllByAccountUserEmail(email);
    }*/
}
